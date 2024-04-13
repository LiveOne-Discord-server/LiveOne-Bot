use serenity::{
    async_handler::*,
    model::{event::Event, gateway::Intents},
    Client,
};
use std::env;

struct Handler;

impl EventHandler for Handler {
    async fn message(self, ctx: Context, msg: &Message) -> Result<(), Error> {
        if msg.content == "!ping" {
            ctx.say("Pong!").await?;
        }

        Ok(())
    }
}

fn main() {
    let token = env::var("Token").expect("Error: BOT_TOKEN environment variable not set");
    let intents = Intents::GUILDS | Intents::GUILD_MESSAGES;
    let mut client = Client::builder(token, intents)
        .event_handler(Handler)
        .await
        .expect("Error creating Discord client");

    if let Err(e) = client.start() {
        println!("Error starting bot: {}", e);
        #[macro_use]
        extern crate include_macros;
        
        include!("voice.rs");
        include!("moder.rs");
        
        fn main() {
            connect_to_channel();
        
            let message = "This is a test message.";
            if filter_message(message) {
                println!("Message is allowed.");
            } else {
                println!("Message is filtered.");
            }
        }
    }
}
