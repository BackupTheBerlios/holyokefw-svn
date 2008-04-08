
import com.bubble.serializer.ConstSaverFilter;
import com.bubble.serializer.DeserializationContext;
import com.bubble.serializer.SaverFilter;
import com.bubble.serializer.SerializationContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;


public class Test {

public static void main(String[] args) throws Exception
{
	int[] data = new int[100];
	for (int i=0; i<data.length; ++i) data[i] = i;
	
	SaverFilter sfilter = new ConstSaverFilter(false);
	SerializationContext scon = new SerializationContext(sfilter);
	ByteBuffer buf = ByteBuffer.allocate(1024);
	FileOutputStream fout = new FileOutputStream("xx");
	DataOutputStream dfout = new DataOutputStream(fout);
//	FileChannel fc = fout.getChannel();
	
	scon.serialize(data, dfout);
	scon.serialize(data, dfout);
	dfout.close();

	// Deserialize
	DeserializationContext dcon = new DeserializationContext(Test.class.getClassLoader(), sfilter);
	DataInputStream din = new DataInputStream(new FileInputStream("xx"));
	System.out.println(dcon.deserialize(din));
	System.out.println(dcon.deserialize(din));
	
	
//	buf.flip();
//	buf.limit(100);
//	Object o = dcon.deserialize(buf);
//	System.out.println(o);
//	fc.write(buf);
//	fc.close();
}

}
