/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: HuffmanNode.java,v 1.2 2003/04/10 19:48:31 jarnbjo Exp $
 * -----------------------------------------------------------
 *
 * $Author: jarnbjo $
 *
 * Description:
 *
 * Copyright 2002-2003 Tor-Einar Jarnbjo
 * -----------------------------------------------------------
 *
 * Change History
 * -----------------------------------------------------------
 * $Log: HuffmanNode.java,v $
 * Revision 1.2  2003/04/10 19:48:31  jarnbjo
 * no message
 *
 */

package de.jarnbjo.util.io;

import java.io.IOException;

/**
 * Representation of a node in a Huffman tree, used to read Huffman compressed
 * codewords from e.g. a Vorbis stream.
 */

final public class HuffmanNode {

    private int depth = 0;
    protected HuffmanNode o0, o1;
    protected Integer value;
    private boolean full = false;

    /**
     * creates a new Huffman tree root node
     */

    public HuffmanNode() {
        this(null);
    }

    protected HuffmanNode(HuffmanNode parent) {
        if (parent != null) {
            depth = parent.getDepth() + 1;
        }
    }

    protected HuffmanNode(HuffmanNode parent, int value) {
        this(parent);
        this.value = value;
        full = true;
    }

    protected HuffmanNode get0() {
        return o0 == null ? set0(new HuffmanNode(this)) : o0;
    }

    protected HuffmanNode get1() {
        return o1 == null ? set1(new HuffmanNode(this)) : o1;
    }

    protected int getDepth() {
        return depth;
    }

    protected Integer getValue() {
        return value;
    }

    private boolean isFull() {
        return full || (full = o0 != null && o0.isFull() && o1 != null && o1.isFull());
    }

    protected int read(BitInputStream bis) throws IOException {
        HuffmanNode iter = this;
        while (iter.value == null) {
            iter = bis.getBit() ? iter.o1 : iter.o0;
        }
        return iter.value;
    }

    private HuffmanNode set0(HuffmanNode value) {
        return o0 = value;
    }

    private HuffmanNode set1(HuffmanNode value) {
        return o1 = value;
    }

    /**
     * creates a new tree node at the first free location at the given depth,
     * and assigns the value to it
     * 
     * @param depth
     *            the tree depth of the new node (codeword length in bits)
     * @param value
     *            the node's new value
     */

    public boolean setNewValue(int depth, int value) {
        if (isFull()) {
            return false;
        }
        if (depth == 1) {
            if (o0 == null) {
                set0(new HuffmanNode(this, value));
                return true;
            } else if (o1 == null) {
                set1(new HuffmanNode(this, value));
                return true;
            } else {
                return false;
            }
        } else {
            return get0().setNewValue(depth - 1, value) || get1().setNewValue(depth - 1,
                    value);
        }
    }
}
