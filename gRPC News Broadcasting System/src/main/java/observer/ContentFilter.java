package observer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A helper class for managing blocked words and redaction policies.
 * Processes news content to ensure it adheres to blocking rules before broadcasting.
 */
public class ContentFilter {
    private Map<String, Boolean> blockedWords = new HashMap<>();

    /**
     * Adds a word to the block list, with an option to redact it in the news.
     * @param word The word to block.
     * @param redact If true, redact the word (replace with '#'); otherwise, block the entire news containing it.
     * @return true if the word is successfully blocked, false otherwise.
     */
    public boolean blockWord(String word, boolean redact) {
        if (word == null || word.trim().isEmpty() || !word.matches("^[a-zA-Z]+$")) {
            return false; // Reject invalid or empty words
        }
        blockedWords.put("\\b" + Pattern.quote(word.trim()) + "\\b", redact);
        return true;
    }

    /**
     * Removes a word from the block list.
     * @param word The word to unblock.
     * @return true if the word was previously blocked and is now unblocked, false otherwise.
     */
    public boolean unblockWord(String word) {
        return blockedWords.remove("\\b" + Pattern.quote(word.trim()) + "\\b") != null;
    }

    /**
     * Processes the news content by applying blocking or redaction rules.
     * @param news The news content to process.
     * @return The processed news (with blocked words handled).
     * @throws BlockedContentException if the news contains non-redacted blocked words.
     */
    public String processNews(String news) throws BlockedContentException {
        for (Map.Entry<String, Boolean> entry : blockedWords.entrySet()) {
            if (news.matches(".*" + entry.getKey() + ".*")) {
                if (entry.getValue()) {
                    // Redact the word (replace with '#')
                    news = news.replaceAll(entry.getKey(), "#");
                } else {
                    // Throw exception for non-redacted blocked content
                    throw new BlockedContentException("News contains blocked content.");
                }
            }
        }
        return news; // Return the processed news
    }
}

