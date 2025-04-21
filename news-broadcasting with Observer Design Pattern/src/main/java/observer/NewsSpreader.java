package observer;

/**
 * An interface for spreading news.
 */
public interface NewsSpreader {
	
	
	/**
	 * Registers a trusted news-source.
	 * 
	 * @param source a string used to identify the source
	 * @param pwd    a password that allows to authenticate the source when
	 *               spreading news
	 * @return false if source is null or already registered or if pwd is null or empty , true otherwise
	 */
	public boolean registerTrustedSource(String source, String pwd);


	/**
	 * Registers a word to be blocked from spreading. All behaviors on blocked words is case-insensitive.
	 * 
	 * @param word2block a word that must not be spread in subsequent calls to `spreadNews`. 
	 * If the same word has already been registered for blocking, it applies the new redacting status to the word
	 * @param redact if true, every occurrence of word2block in news to be spread is replaced with one single #
	 * 	if false, the entire piece of news that includes the word must not be spread (see `spreadNews`)
	 * @return true if successful, false for invalid arguments or if word2block is not a word
	 */
	public boolean blockWord(String word2block, boolean redact);
	
	/**
	 * Unblocks a word for spreading. Cancels the effect of any previous `blockWord` with the same word.
	 * 
	 * @param word2free A word that maybe was previously blocked.
	 * @return true if parameter is valid and a word, false otherwise
	 */
	public boolean unblockWord(String word2free);	
	
	/**
	 * Sends the `news` string to every registered news-receiver (respecting the blocking policy as configured with `blockWord`).
	 * 
	 * @param news   a string that contains the news to be spread
	 * @param source the source of the news (which must be already registered)
	 * @param pwd    the password (must match the registered password for this source)
	 * @return the actual piece of news spread
	 * @throws IllegalArgumentException if at least one argument is null; otherwise, see below
	 * @throws UntrustedSourceException when the source was not registered before
	 * @throws AuthenticationException  when the source was registered with a different password
	 * @throws BlockedContentException when authentication ok, but news contains blocked content with no redaction
	 */
	public String spreadNews(String news, String source, String pwd)
			throws NewsSpreaderException;

}