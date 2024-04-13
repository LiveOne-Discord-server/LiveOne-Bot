use serenity::{
    async_handler::*,
    model::{channel::Message, gateway::Ready},
    Client,
};

struct Handler;

impl EventHandler for Handler {
    async fn message(&self, ctx: Context, message: &Message) -> Option<Command> {
        if let Some(channel_id) = message.channel_id {
            if channels.contains(&channel_id.to_string()) {
            }
        }

        None
    }

    async fn ready(&self, ctx: Context, ready: Ready) {
        let guild = ready.guilds.get(0).expect("Не удалось получить гильдию");

        let channels = vec![
        ];

        self.monitor_channels(channels);
    }
}

struct DiscordBot {
    client: Client,
}

impl DiscordBot {
    async fn start(&self) {
        let client = Client::builder(&token)
            .event_handler(Handler)
            .await
            .expect("Не удалось создать клиента");

        await client.start().expect("Не удалось запустить бота");
    }

    fn monitor_channels(&self, channels: Vec<String>) {
        for channel_id in channels {
            let channel = self.client.cache.guild_channel(&guild_id, &channel_id).expect("Не удалось найти канал");

            channel.messages().subscribe(|event| async {
                let message = event.message;
            });
        }
    }
}
