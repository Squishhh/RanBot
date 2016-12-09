
package ranbot.command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import ranbot.command.chooser.BiasedRandomChooser;
import ranbot.command.chooser.ChooserCommandHandler;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class CommandListener implements IListener<MessageReceivedEvent> {

	private final Map<String, CommandHandler> actionCommandHandlerMap;

	public CommandListener() {
		actionCommandHandlerMap = new HashMap<>();

		final ChooserCommandHandler chooseChooser = new ChooserCommandHandler(new BiasedRandomChooser());
		final KickCommandHandler kickCommand = new KickCommandHandler();
//		final ImageUploaderCommandHandler lewdRanUploader = new ImageUploaderCommandHandler(
//				new File("Insert lood pics directory here"),
//				(dir, filename) -> filename.toLowerCase().contains("ran yakumo"));
		actionCommandHandlerMap.put("kick", kickCommand);
		actionCommandHandlerMap.put("choose", chooseChooser);
		//actionCommandHandlerMap.put("lood", lewdRanUploader);
	}

	@Override
	public void handle(MessageReceivedEvent event) {
		IMessage message = event.getMessage();
		MessageCommand command = getCommand(message);

		if (command == null) {
			System.out.println("Not a command.");
			return;
		}

		System.out.println("Received command: " + command);
		CommandHandler handler = actionCommandHandlerMap.get(command.getAction());
		if (handler == null) {
			System.out.println("Unsupported.");
			return;
		}

		try {
			handler.processCommand(command);
		} catch (DiscordException | MissingPermissionsException | RateLimitException e) {
			e.printStackTrace();
		}

//		if (command.equalsIgnoreCase("fucfuc")) {
//			try {
//				message.getChannel().sendMessage("I want it 2000 times ;)");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else if (command.equalsIgnoreCase("strip")) {
//			try {
//				message.getChannel().sendMessage("Stop being naughty, boi. Soon ;)");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}

	private MessageCommand getCommand(IMessage message) {
		String content = message.getContent().trim();

		int spaceIndex = content.indexOf(" ");
		if (spaceIndex == -1 || content.substring(0, spaceIndex).indexOf(":ran:") != 1) {
			System.out.println("message not a command: " + content);
			return null;
		}

		String actionArg = content.substring(spaceIndex + 1);
		int actionSpaceIndex = actionArg.indexOf(" ");
		if (actionSpaceIndex == -1) {
			return new MessageCommand(message, actionArg, null);
		} else {
			String action = actionArg.substring(0, actionSpaceIndex);
			String arg = actionArg.substring(actionSpaceIndex + 1);
			return new MessageCommand(message, action, arg);
		}
	}

}
