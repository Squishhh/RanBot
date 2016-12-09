package ranbot.command.chooser;

import static java.util.Objects.requireNonNull;

import ranbot.command.CommandHandler;
import ranbot.command.MessageCommand;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class ChooserCommandHandler implements CommandHandler {
	
	private final Chooser chooser;

	public ChooserCommandHandler(Chooser chooser) {
		this.chooser = requireNonNull(chooser);
	}

	@Override
	public void processCommand(MessageCommand messageCommand)
			throws DiscordException, MissingPermissionsException, RateLimitException {
		final IChannel channel = messageCommand.getChannel();
		String[] choices = messageCommand.getArg().split("\\Q | \\E");

		if (choices.length == 1) {
			channel.sendMessage("There is nothing to choose >:(");
			return;
		}
		
		String chosen = chooser.choose(choices);
		String message = chooser.getChoiceStatement(chosen);
		channel.sendMessage(message);
	}
}
