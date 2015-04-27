package pasCode;

public 

class ThreadTest implements Runnable
{
	String name="";
	public ThreadTest(String n)
	{
		name=n;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int count =0;
		while(count<50)
		{
			try{
				System.out.println(name+ " " + count);
				count++;
				Thread.sleep(50);	
			}
		catch(Exception e)
		{}
		}
	}
	
	public void start()
	{
		System.out.println(name+"Thread Starting..");
		Thread t = new Thread(this);
		t.start();
		
	}
}