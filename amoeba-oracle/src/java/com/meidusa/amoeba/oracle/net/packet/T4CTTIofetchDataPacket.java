package com.meidusa.amoeba.oracle.net.packet;


public class T4CTTIofetchDataPacket extends T4CTTIfunPacket {

    int cursor;
    int al8i4_1;

    public T4CTTIofetchDataPacket(){
        super(OFETCH);
    }

    // @Override
    // protected void init(AbstractPacketBuffer buffer) {
    // super.init(buffer);
    // T4CPacketBuffer meg = (T4CPacketBuffer) buffer;
    // cursor = meg.unmarshalSWORD();
    // al8i4_1 = meg.unmarshalSWORD();
    // }
    //
    // @Override
    // protected void write2Buffer(AbstractPacketBuffer buffer) throws UnsupportedEncodingException {
    // super.write2Buffer(buffer);
    // }

}
