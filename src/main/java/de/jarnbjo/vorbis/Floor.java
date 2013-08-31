/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: Floor.java,v 1.3 2003/04/10 19:49:04 jarnbjo Exp $
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
 * $Log: Floor.java,v $
 * Revision 1.3  2003/04/10 19:49:04  jarnbjo
 * no message
 *
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 *
 *
 */

package de.jarnbjo.vorbis;

import java.io.IOException;

import de.jarnbjo.util.io.BitInputStream;

public abstract class Floor {

    public final static float[] DB_STATIC_TABLE = { 1.0649863e-07f,
	    1.1341951e-07f, 1.2079015e-07f, 1.2863978e-07f, 1.3699951e-07f,
	    1.4590251e-07f, 1.5538408e-07f, 1.6548181e-07f, 1.7623575e-07f,
	    1.8768855e-07f, 1.9988561e-07f, 2.128753e-07f, 2.2670913e-07f,
	    2.4144197e-07f, 2.5713223e-07f, 2.7384213e-07f, 2.9163793e-07f,
	    3.1059021e-07f, 3.3077411e-07f, 3.5226968e-07f, 3.7516214e-07f,
	    3.9954229e-07f, 4.2550680e-07f, 4.5315863e-07f, 4.8260743e-07f,
	    5.1396998e-07f, 5.4737065e-07f, 5.8294187e-07f, 6.2082472e-07f,
	    6.6116941e-07f, 7.0413592e-07f, 7.4989464e-07f, 7.9862701e-07f,
	    8.5052630e-07f, 9.0579828e-07f, 9.6466216e-07f, 1.0273513e-06f,
	    1.0941144e-06f, 1.1652161e-06f, 1.2409384e-06f, 1.3215816e-06f,
	    1.4074654e-06f, 1.4989305e-06f, 1.5963394e-06f, 1.7000785e-06f,
	    1.8105592e-06f, 1.9282195e-06f, 2.0535261e-06f, 2.1869758e-06f,
	    2.3290978e-06f, 2.4804557e-06f, 2.6416497e-06f, 2.8133190e-06f,
	    2.9961443e-06f, 3.1908506e-06f, 3.3982101e-06f, 3.6190449e-06f,
	    3.8542308e-06f, 4.1047004e-06f, 4.3714470e-06f, 4.6555282e-06f,
	    4.9580707e-06f, 5.2802740e-06f, 5.6234160e-06f, 5.9888572e-06f,
	    6.3780469e-06f, 6.7925283e-06f, 7.2339451e-06f, 7.7040476e-06f,
	    8.2047000e-06f, 8.7378876e-06f, 9.3057248e-06f, 9.9104632e-06f,
	    1.0554501e-05f, 1.1240392e-05f, 1.1970856e-05f, 1.2748789e-05f,
	    1.3577278e-05f, 1.4459606e-05f, 1.5399272e-05f, 1.6400004e-05f,
	    1.7465768e-05f, 1.8600792e-05f, 1.9809576e-05f, 2.1096914e-05f,
	    2.2467911e-05f, 2.3928002e-05f, 2.5482978e-05f, 2.7139006e-05f,
	    2.8902651e-05f, 3.0780908e-05f, 3.2781225e-05f, 3.4911534e-05f,
	    3.7180282e-05f, 3.9596466e-05f, 4.2169667e-05f, 4.4910090e-05f,
	    4.7828601e-05f, 5.0936773e-05f, 5.4246931e-05f, 5.7772202e-05f,
	    6.1526565e-05f, 6.5524908e-05f, 6.9783085e-05f, 7.4317983e-05f,
	    7.9147585e-05f, 8.4291040e-05f, 8.9768747e-05f, 9.5602426e-05f,
	    0.00010181521f, 0.00010843174f, 0.00011547824f, 0.00012298267f,
	    0.00013097477f, 0.00013948625f, 0.00014855085f, 0.00015820453f,
	    0.00016848555f, 0.00017943469f, 0.00019109536f, 0.00020351382f,
	    0.00021673929f, 0.00023082423f, 0.00024582449f, 0.00026179955f,
	    0.00027881276f, 0.00029693158f, 0.00031622787f, 0.00033677814f,
	    0.00035866388f, 0.00038197188f, 0.00040679456f, 0.00043323036f,
	    0.00046138411f, 0.00049136745f, 0.00052329927f, 0.00055730621f,
	    0.00059352311f, 0.00063209358f, 0.00067317058f, 0.00071691700f,
	    0.00076350630f, 0.00081312324f, 0.00086596457f, 0.00092223983f,
	    0.00098217216f, 0.0010459992f, 0.0011139742f, 0.0011863665f,
	    0.0012634633f, 0.0013455702f, 0.0014330129f, 0.0015261382f,
	    0.0016253153f, 0.0017309374f, 0.0018434235f, 0.0019632195f,
	    0.0020908006f, 0.0022266726f, 0.0023713743f, 0.0025254795f,
	    0.0026895994f, 0.0028643847f, 0.0030505286f, 0.0032487691f,
	    0.0034598925f, 0.0036847358f, 0.0039241906f, 0.0041792066f,
	    0.0044507950f, 0.0047400328f, 0.0050480668f, 0.0053761186f,
	    0.0057254891f, 0.0060975636f, 0.0064938176f, 0.0069158225f,
	    0.0073652516f, 0.0078438871f, 0.0083536271f, 0.0088964928f,
	    0.009474637f, 0.010090352f, 0.010746080f, 0.011444421f,
	    0.012188144f, 0.012980198f, 0.013823725f, 0.014722068f,
	    0.015678791f, 0.016697687f, 0.017782797f, 0.018938423f,
	    0.020169149f, 0.021479854f, 0.022875735f, 0.024362330f,
	    0.025945531f, 0.027631618f, 0.029427276f, 0.031339626f,
	    0.033376252f, 0.035545228f, 0.037855157f, 0.040315199f,
	    0.042935108f, 0.045725273f, 0.048696758f, 0.051861348f,
	    0.055231591f, 0.058820850f, 0.062643361f, 0.066714279f,
	    0.071049749f, 0.075666962f, 0.080584227f, 0.085821044f,
	    0.091398179f, 0.097337747f, 0.10366330f, 0.11039993f, 0.11757434f,
	    0.12521498f, 0.13335215f, 0.14201813f, 0.15124727f, 0.16107617f,
	    0.17154380f, 0.18269168f, 0.19456402f, 0.20720788f, 0.22067342f,
	    0.23501402f, 0.25028656f, 0.26655159f, 0.28387361f, 0.30232132f,
	    0.32196786f, 0.34289114f, 0.36517414f, 0.38890521f, 0.41417847f,
	    0.44109412f, 0.46975890f, 0.50028648f, 0.53279791f, 0.56742212f,
	    0.60429640f, 0.64356699f, 0.68538959f, 0.72993007f, 0.77736504f,
	    0.82788260f, 0.88168307f, 0.9389798f, 1.0f };

    static Floor createInstance(BitInputStream source, SetupHeader header)
	    throws VorbisFormatException, IOException {

	int type = source.getInt(16);
	switch (type) {
	case 0:
	    return new Floor0(source, header);
	case 1:
	    return new Floor1(source, header);
	default:
	    throw new VorbisFormatException("Floor type " + type
		    + " is not supported.");
	}
    }

    abstract int getType();

    abstract Floor decodeFloor(VorbisStream vorbis, BitInputStream source)
	    throws VorbisFormatException, IOException;

    abstract void computeFloor(float[] vector);
}