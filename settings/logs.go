package main

import (
	"fmt"
	"log"
	"os"
	"strings"

	"github.com/bwmarrin/discordgo"
	"github.com/joho/godotenv"
)

type GifSearch struct {
	Data struct {
		EmbedUrl string `json:"embed_url"`
	} `json:"data"`
}

func messageCreate(s *discordgo.Session, message *discordgo.MessageCreate) {
	err := godotenv.Load(".env")
	giphyToken := os.Getenv("GIPHY_TOKEN")
	if err != nil {
		log.Fatal(err)
	}

	if message.Author.ID == s.State.User.ID {
		return
	}

	command := strings.Split(message.Content, " ")

	if command[0] == "!search" && len(command) > 1 {
		url := "https://api.giphy.com/v1/gifs/random"
		var result GifSearch

		gifKeyword := strings.Join(command[1:], " ")

		req, err := http.NewRequest("GET", url, nil)
		if err != nil {
			fmt.Println("Error in making a new Request", err)
		}

		query := req.URL.Query()
		query.Add("api_key", giphyToken)
		query.Add("tag", gifKeyword)
		req.URL.RawQuery = query.Encode()

		client := http.Client{}
		res, err := client.Do(req)
		if err != nil {
			fmt.Println("Error in getting a response, ", err)
		}

		body, _ := ioutil.ReadAll(res.Body)
		if err := json.Unmarshal(body, &result); err != nil {
			fmt.Println("Can not unmarshall JSON", err)
		}

		s.ChannelMessageSend(message.ChannelID, result.Data.EmbedUrl)
		res.Body.Close()
	}
}

func main() {
	err := godotenv.Load(".env")
	Token := os.Getenv("DISCORD_TOKEN")
	if err != nil {
		log.Fatal(err)
	}

	dg, err := discordgo.New("Bot " + Token)
	if err != nil {
		fmt.Println("Error creating a discord Session, ", err)
	}

	dg.AddHandler(ready)
	dg.AddHandler(messageCreate)

	err = dg.Open()
	if err != nil {
		fmt.Println("Error opening Discord Session, ", err)
	}

	fmt.Println("The bot is now running. Press CTRL-C to exit.")

	sc := make(chan os.Signal, 1)
	signal.Notify(sc, syscall.SIGINT, syscall.SIGTERM, os.Interrupt, os.Kill)
	<-sc
}

func ready(s *discordgo.Session, event *discordgo.Ready) {
	fmt.Println("Bot is ready!")
}