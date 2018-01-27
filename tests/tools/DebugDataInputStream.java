package tools;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DebugDataInputStream implements DataInput, AutoCloseable {
	private final DataInputStream stream;
	
	public DebugDataInputStream(InputStream inStream) {
		this.stream = new DataInputStream(inStream);
	}

	@Override
	public boolean readBoolean() throws IOException {
		final boolean v = stream.readBoolean();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public byte readByte() throws IOException {
		final byte v = stream.readByte();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public char readChar() throws IOException {
		final char v = stream.readChar();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public double readDouble() throws IOException {
		final double v = stream.readDouble();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public float readFloat() throws IOException {
		final float v = stream.readFloat();
		System.out.print(" " + v);
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
		System.out.print(" " + v);
		return v;
	}

	@Override
	public String readLine() throws IOException {
		final String v = stream.readLine();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public long readLong() throws IOException {
		final long v = stream.readLong();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public short readShort() throws IOException {
		final short v = stream.readShort();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public String readUTF() throws IOException {
		final String v = stream.readUTF();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public int readUnsignedByte() throws IOException {
		final int v = stream.readUnsignedByte();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public int readUnsignedShort() throws IOException {
		final int v = stream.readUnsignedShort();
		System.out.print(" " + v);
		return v;
	}

	@Override
	public int skipBytes(int n) throws IOException {
		final int v = stream.skipBytes(n);
		System.out.print(" " + v);
		return v;
	}

	@Override
	public void close() throws IOException {
		stream.close();
		System.out.println();
	}
}