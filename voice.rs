#[SerenataCommand]
#[Description("Create a voice channel")]
async fn create_channel(ctx: &Context, cmd: &Command) -> Result<(), SerenataError> {
  let guild_id = cmd.guild_id.ok_or(SerenataError::MissingGuildId)?;
  let channel_name = cmd.args.get(0).ok_or(SerenataError::MissingArguments)?.clone();

let voice_state = ctx.voice(&guild_id).await?;
if voice_state.is_none() {
    ctx.send_message(&cmd.channel_id, "I'm not currently in a voice channel on this server.").await?;
    return Err(SerenataError::NotInVoiceChannel); 
}

let channel_id = voice_state.as_ref().unwrap().channel_id;

let permissions = Permissions::default()
    .add(Permission::VIEW_CHANNEL)
    .add(Permission::CONNECT);

let new_channel = GuildChannel::create(ctx, guild_id, |c| {
    c.name(&channel_name)
        .kind(ChannelType::Voice)
        .parent_id(channel_id)
        .permissions(permissions)
}).await?;


voice_state.as_mut().unwrap().channel_id = new_channel.id;
ctx.send_message(&cmd.channel_id, format!("New voice channel created: {}", new_channel.name)).await?;

Ok(())
}
