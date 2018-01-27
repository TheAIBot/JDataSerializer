package tools;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DebugDataOutputStream implements DataOutput, AutoCloseable {
	private final DataOutputStream stream;
	
	public DebugDataOutputStream(OutputStream outStream) {
		this.stream = new DataOutputStream(outStream);
	}

	@Override
	public void write(int b) throws IOException {
		stream.write(b);
		System.out.print(" " + b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		stream.write(b);
		System.out.print(" " + b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		stream.write(b, off , len);
		System.out.print(" " + b + " " + off + " " + len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		stream.writeBoolean(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		stream.writeByte(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		stream.writeBytes(s);
		System.out.print(" " + s);
	}

	@Override
	public void writeChar(int v) throws IOException {
		stream.writeChar(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeChars(String s) throws IOException {
		stream.writeChars(s);
		System.out.print(" " + s);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		stream.writeDouble(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		stream.writeFloat(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		stream.writeInt(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		stream.writeLong(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeShort(int v) throws IOException {
		stream.writeShort(v);
		System.out.print(" " + v);
	}

	@Override
	public void writeUTF(String s) throws IOException {
		stream.writeUTF(s);
		System.out.print(" " + s);
	}

	@Override
	public void close() throws IOException {
		stream.close();
		System.out.println();
	}
}