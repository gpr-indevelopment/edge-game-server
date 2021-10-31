package com.gpr.edgegameserver.voxelgame;

import org.jcodec.api.SequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class VideoEncoder {

    private final SequenceEncoder sequenceEncoder = SequenceEncoder.createWithFps(NIOUtils.writableChannel(new File("test.mp4")), new Rational(30, 1));

    public VideoEncoder() throws IOException {
    }

    public void encodeJpg(ByteBuffer byteBuffer, int width, int height) {
        try {
            BufferedImage rgb = byteBuffer2BufferedImage(byteBuffer, width, height);
            sequenceEncoder.encodeNativeFrame(AWTUtil.fromBufferedImageRGB(rgb));
        } catch (IOException e) {
            System.out.println("error while encoding video from file");
        }
    }

    public void finish() {
        try {
            sequenceEncoder.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage byteBuffer2BufferedImage(ByteBuffer bb, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, TYPE_INT_ARGB);
        bb.rewind();
        IntBuffer intBuffer = bb.asIntBuffer();
        int[] array = new int[intBuffer.remaining()];
        intBuffer.get(array);
        bi.setRGB(0, 0, width, height, array, 0, width);
        return bi;
    }
}
