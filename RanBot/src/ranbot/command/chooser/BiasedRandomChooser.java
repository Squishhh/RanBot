package ranbot.command.chooser;

/**
 * Randomly chooses from the given choices, but searches the whole choice for
 * it's preferred answer.
 */
public class BiasedRandomChooser extends RandomChooser {

	private boolean checkName(String s) {
		return s.equalsIgnoreCase("squishhh") || s.equalsIgnoreCase("ethereal") || s.equalsIgnoreCase("syaro");
	}

	@Override
	public String choose(String[] choices) {
		int chosen = bias(choices);
		if (chosen == -1) {
			return super.choose(choices);
		} else {
			return choices[chosen];
		}
	}

	/**
	 * Gets a biased choice from the given choices. Returns -1 if no particular
	 * choice is favored.
	 * 
	 * @param choices
	 *            The choices to bias from.
	 * @return The index of the biased choice.
	 */
	private int bias(String[] choices) {
		int chosen = -1;
		for (int i = 0; i < choices.length; i++) {
			if (choices[i].equalsIgnoreCase("yrym")) {
				chosen = 1;
				break;
			} else if (checkName(choices[i])) {
				chosen = i;
			}
		}
		return chosen;
	}

	@Override
	public String getChoiceStatement(String choice) {
		if (choice.equalsIgnoreCase("yrym")) {
			return "I'll choose my bb yrym every time. ;)";
		} else if (checkName(choice)) {
			return "I'd rather choose " + choice + ".";
		} else {
			return super.getChoiceStatement(choice);
		}
	}
}