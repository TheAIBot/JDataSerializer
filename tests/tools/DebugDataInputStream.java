package tools;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DebugDataInputStream implements DataInput, AutoCloseable {
	private final DataInputStream stream;
	private final StringBuilder sBuilder = new StringBuilder();
	
	public DebugDataInputStream(InputStream inStream) {
		this.stream = new DataInputStream(inStream);
	}

	@Override
	public boolean readBoolean() throws IOException {
		final boolean v = stream.readBoolean();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public byte readByte() throws IOException {
		final byte v = stream.readByte();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public char readChar() throws IOException {
		final char v = stream.readChar();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public double readDouble() throws IOException {
		final double v = stream.readDouble();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public float readFloat() throws IOException {
		final float v = stream.readFloat();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int readInt() throws IOException {
		final int v = stream.readInt();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public String readLine() throws IOException {
		final String v = stream.readLine();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public long readLong() throws IOException {
		final long v = stream.readLong();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public short readShort() throws IOException {
		final short v = stream.readShort();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public String readUTF() throws IOException {
		final String v = stream.readUTF();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public int readUnsignedByte() throws IOException {
		final int v = stream.readUnsignedByte();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		final int v = stream.readUnsignedShort();
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public int skipBytes(int n) throws IOException {
		final int v = stream.skipBytes(n);
		sBuilder.append(" " + v);
		return v;
	}

	@Override
	public void close() throws IOException {
		stream.close();
	}
	
	public String dataAsString() {
		return sBuilder.toString();
	}
	
	public void addDataAsString(String data) {
		sBuilder.append(data);
	}
	
	public void print() {
		System.out.println(sBuilder.toString());
	}
}