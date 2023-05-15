package lucene_services;

import lucene_handlers.UserInputHandler;

public class UserInteractionService {
    private UserInputHandler userInputHandler;

    public UserInteractionService() {
        this.userInputHandler = new UserInputHandler();
    }

    public String getIndexDirPath() {
        return System.getProperty("user.home") + userInputHandler.getInput("Please enter the path to the index directory:");
    }

    public String getDataDirPath() {
        return System.getProperty("user.home") + userInputHandler.getInput("Please enter the path to the data directory:");
    }

    public String getQuery() {
        return userInputHandler.getInput("Please enter the search query:");
    }

    public int getNumHits(int maxFiles) {
        return userInputHandler.getIntInput("Please enter the maximum number of search results:", maxFiles);
    }

    public boolean wantsToSaveResults() {
        String saveResults = userInputHandler.getInput("Do you want to save the search results to a directory? (Y/N)");
        return saveResults.equalsIgnoreCase("Y");
    }

    public String getSaveDirPath() {
        return System.getProperty("user.home") + userInputHandler.getInput("Please enter the path to the directory where you want to store the search results:");
    }
}
