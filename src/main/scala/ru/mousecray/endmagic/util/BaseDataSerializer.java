package ru.mousecray.endmagic.util;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;

import java.io.IOException;
import java.util.function.Function;

public class BaseDataSerializer<A> implements DataSerializer<A> {

    private final String name;
    private final WriteValue<A> write;
    private final Function<PacketBuffer, A> read;

    public BaseDataSerializer(String name, WriteValue<A> write, Function<PacketBuffer, A> read) {
        this.name = name;
        this.write = write;
        this.read = read;
        DataSerializers.registerSerializer(this);
    }

    @Override
    public void write(PacketBuffer buf, A value) {
        write.apply(buf, value);
    }

    @Override
    public A read(PacketBuffer buf) throws IOException {
        return read.apply(buf);
    }

    @Override
    public DataParameter<A> createKey(int id) {
        return new DataParameter<A>(id, this) {
            @Override
            public String toString() {
                return "DataParameter(" + name + ")";
            }
        };
    }

    @Override
    public A copyValue(A value) {
        return value;
    }

    public interface WriteValue<A> {
        void apply(PacketBuffer buf, A value);
    }
}
