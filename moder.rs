use serenity::{
    async_handler::*,
    client::Context,
    framework::standard::CommandResult,
    model::{channel::Message, gateway::Ready},
    Client,
};

struct Handler;

#[derive(Default)]
struct MyBot {
    channels: Vec<String>,
    guild_id: Option<u64>,
}

impl EventHandler for Handler {
    async fn message(&self, ctx: Context, message: &Message) -> Option<CommandResult> {

        None
    }

    async fn ready(&self, ctx: Context, ready: Ready) {
        let guild = ready.guilds.get(0).expect("Не удалось получить гильдию");
        let my_bot = ctx.data.read().await.get::<MyBot>().expect("Не удалось получить данные бота");

        my_bot.guild_id = Some(guild.id);
        my_bot.monitor_channels(&ctx).await;
    }
}

impl MyBot {
    async fn monitor_channels(&self, ctx: &Context) -> Result<(), serenity::Error> {
        for channel_id in &self.channels {
            let channel = match self.guild_id {
                Some(guild_id) => ctx.cache.guild_channel(guild_id, channel_id)
                    .await?,
                None => return Err(serenity::Error::InvalidGuildId),
            };

            channel.messages().subscribe(|event| async {
                let message = event.message;
            }).await;
        }

        Ok(())
    }

    async fn start(&self) -> Result<(), serenity::Error> {
        let client = Client::builder(&token)
            .event_handler(Handler)
            .await
            .expect("Не удалось создать клиента");

        client.data.write().await.insert::<MyBot>(self.clone());

        client.start().await
    }
}

fn main() {
    let channels = vec!["channel_id_1".to_string(), "channel_id_2".to_string()]; 
    let bot = MyBot { channels };

    bot.start().await.expect("Не удалось запустить бота");
}
