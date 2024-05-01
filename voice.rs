#[SerenataCommand]
#[Description("Create a voice channel")]
async fn create_channel(ctx: &Context, cmd: &Command) -> Result<(), SerenataError> {
    let guild_id = cmd.guild_id.ok_or(SerenataError::MissingGuildId)?;
    let channel_name = cmd.args.get(0).ok_or(SerenataError::MissingArguments)?.clone();
    let voice_state = ctx.voice(&guild_id).ok_or(SerenataError::NotInVoiceChannel)?;
    let channel_id = voice_state.channel_id;

    let new_channel = GuildChannel::create(ctx, guild_id, |c| {
        c.name(&channel_name)
         .kind(ChannelType::Voice)
         .parent_id(channel_id)
    })
    .await?;

    voice_state.modify(|v| v.channel_id(new_channel.id)).await?;
    ctx.send_message(&channel_id, format!("New voice channel created: {}", new_channel.name)).await?;

    Ok(())
}
