package main

import (
	"fmt"
	"log"
	"os"
	"strings"
	"time"

	"github.com/bwmarrin/discordgo"
	"github.com/joho/godotenv"
	"golang.org/x/net/context"
	"golang.org/x/oauth2"
	"golang.org/x/oauth2/gitlab"
)

type GitLab struct {
	Token string `json:"token"`
}

type DiscordMessage struct {
	Content string `json:"content"`
	Author  struct {
		ID string `json:"id"`
	} `json:"author"`
}

func main() {
	err := godotenv.Load(".env")
	if err != nil {
		log.Fatal(err)
	}

	discordToken := os.Getenv("****")
	gitLabToken := os.Getenv("****")

	dg, err := discordgo.New("Bot " + discordToken)
	if err != nil {
		fmt.Println("Error creating a discord Session, ", err)
	}

	dg.AddHandler(messageCreate)

	err = dg.Open()
	if err != nil {
		fmt.Println("Error opening Discord Session, ", err)
	}

	fmt.Println("The bot is now running. Press CTRL-C to exit.")

	sc := make(chan os.Signal, 1)
	signal.Notify(sc, syscall.SIGINT, syscall.SIGTERM, os.Interrupt, os.Kill)
	<-sc

	ctx := context.Background()
	ts := oauth2.StaticTokenSource(&oauth2.Token{AccessToken: gitLabToken})
	tc := oauth2.NewClient(ctx, ts)
	gitLabClient := gitlab.NewClient(tc)

	messages, err := dg.ChannelMessages("channel_id", 100, "", "", "")
	if err != nil {
		fmt.Println("Error getting messages, ", err)
	}


	var messageCount int
	var userMessageCount map[string]int
	userMessageCount = make(map[string]int)

	for _, message := range messages {
		messageCount++
		userMessageCount[message.Author.ID]++
	}


	gitLabProjectID := "project_id"
	gitLabIssueTitle := "Chat Statistics"
	gitLabIssueBody := fmt.Sprintf("Message count: %d\nUser message count:\n", messageCount)
	for user, count := range userMessageCount {
		gitLabIssueBody += fmt.Sprintf("- %s: %d\n", user, count)
	}

	issue, _, err := gitLabClient.Issues.CreateIssue(ctx, gitLabProjectID, &gitlab.Issue{
		Title: &gitLabIssueTitle,
		Description: &gitLabIssueBody,
	})
	if err != nil {
		fmt.Println("Error creating issue, ", err)
	}

	fmt.Println("Issue created:", issue.IID)
}