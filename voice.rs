#[SerenataCommand]
#[Description("Создает голосовой канал")]
async fn create_channel(ctx: &Context, cmd: &Command) -> Result<(), Box<dyn Error>> {
    let guild_id = cmd.guild_id.unwrap();
    let channel_name = cmd.args[0].clone();

    let voice_state = ctx.voice(&guild_id);
    if voice_state.is_none() {
        return Err(Box::new(Error::from("Вы должны быть в голосовом канале, чтобы использовать эту команду.")));
    }

    let voice_state = voice_state.unwrap();
    let channel_id = voice_state.channel_id;

    let new_channel = GuildChannel::create(ctx, guild_id, |c| {
        c.name(&channel_name);
        c.kind(ChannelType::Voice);
        c.parent_id(channel_id);
    })
    .await?;

    voice_state.modify(|v| v.channel_id(new_channel.id)).await?;
    let msg = format!("Создан новый голосовой канал: {}", new_channel.name);
    ctx.send_message(&channel_id, msg).await?;

    Ok(())
}
