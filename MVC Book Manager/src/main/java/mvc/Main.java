package mvc;

import mvc.presentation.MainFX;

public class Main {

  public static void main(String[] args) throws InterruptedException {
	Thread t = new Thread() {
		public void run() {
			MainFX.launch(MainFX.class, args);
		}
	};
	t.start();
	
  }
}
