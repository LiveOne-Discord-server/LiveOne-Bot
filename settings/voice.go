package main

import (
	"fmt"
	"log"
	"os"
	"strings"
	"time"

	"github.com/bwmarrin/discordgo"
	"github.com/joho/godotenv"
)

type VoiceConnection struct {
	*discordgo.VoiceConnection
}

func main() {
	err := godotenv.Load(".env")
	if err!= nil {
		log.Fatal(err)
	}

	discordToken := os.Getenv("DISCORD_TOKEN")

	dg, err := discordgo.New("Bot " + discordToken)
	if err!= nil {
		fmt.Println("Error creating a discord Session, ", err)
	}

	dg.AddHandler(messageCreate)
	dg.AddHandler(ready)
	dg.AddHandler(voiceStateUpdate)

	err = dg.Open()
	if err!= nil {
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

func messageCreate(s *discordgo.Session, message *discordgo.MessageCreate) {
	if message.Author.ID == s.State.User.ID {
		return
	}

	if message.Content == "!join" {
		channel, err := s.State.Channel(message.ChannelID)
		if err!= nil {
			fmt.Println("Error getting channel, ", err)
			return
		}

		vc, err := s.VoiceChannelJoin(channel.GuildID, channel.ID, false, true)
		if err!= nil {
			fmt.Println("Error joining voice channel, ", err)
			return
		}

		fmt.Println("Joined voice channel")
	}

	if message.Content == "!leave" {
		for _, vs := range s.VoiceConnections {
			vs.Disconnect()
		}

		fmt.Println("Left voice channel")
	}
}

func voiceStateUpdate(s *discordgo.Session, vsu *discordgo.VoiceStateUpdate) {
	if vsu.UserID == s.State.User.ID {
		return
	}

	if vsu.ChannelID!= "" {
		fmt.Println("User", vsu.UserID, "joined voice channel", vsu.ChannelID)
	} else {
		fmt.Println("User", vsu.UserID, "left voice channel")
	}
}