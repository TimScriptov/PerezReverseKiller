package com.perez.xml2axml.chunks;

import android.graphics.Color;

import com.perez.xml2axml.ComplexConsts;
import com.perez.xml2axml.IntWriter;
import com.perez.xml2axml.NotImplementedException;
import com.perez.xml2axml.ValueType;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttrChunk extends Chunk<Chunk.EmptyHeader> {
    private StartTagChunk startTagChunk;
    public String prefix;
    public String name;
    public String namespace;
    public String rawValue;

    public AttrChunk(StartTagChunk startTagChunk) {
        super(startTagChunk);
        this.startTagChunk = startTagChunk;
        header.size = 20;
    }

    public ValueChunk value = new ValueChunk(this);

    @Override
    public void preWrite() {
        value.calc();
    }

    @Override
    public void writeEx(IntWriter w) throws IOException {
        w.write(startTagChunk.stringIndex(null, namespace));
        w.write(startTagChunk.stringIndex(namespace, name));

        if(value.type == ValueType.STRING)
            w.write(startTagChunk.stringIndex(null, rawValue));
        else
            w.write(-1);
        value.write(w);
    }
}
