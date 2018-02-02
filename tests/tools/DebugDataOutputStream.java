package tools;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DebugDataOutputStream implements DataOutput, AutoCloseable {
	private final DataOutputStream stream;
	private final StringBuilder sBuilder = new StringBuilder();
	
	public DebugDataOutputStream(OutputStream outStream) {
		this.stream = new DataOutputStream(outStream);
	}

	@Override
	public void write(int b) throws IOException {
		stream.write(b);
		sBuilder.append(" " + b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		stream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		stream.write(b, off , len);
		sBuilder.append(" " + b + " " + off + " " + len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		stream.writeBoolean(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		stream.writeByte(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		stream.writeBytes(s);
		sBuilder.append(" " + s);
	}

	@Override
	public void writeChar(int v) throws IOException {
		stream.writeChar(v);
		sBuilder.append(" " + (char)v);
	}

	@Override
	public void writeChars(String s) throws IOException {
		stream.writeChars(s);
		sBuilder.append(" " + s);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		stream.writeDouble(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		stream.writeFloat(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		stream.writeInt(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		stream.writeLong(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeShort(int v) throws IOException {
		stream.writeShort(v);
		sBuilder.append(" " + v);
	}

	@Override
	public void writeUTF(String s) throws IOException {
		stream.writeUTF(s);
		sBuilder.append(" " + s);
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