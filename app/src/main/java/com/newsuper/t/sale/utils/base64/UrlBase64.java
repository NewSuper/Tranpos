package com.newsuper.t.sale.utils.base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UrlBase64
{
    private static final Encoder encoder = new UrlBase64Encoder();
    
    /**
     * Encode the input data producing a URL safe base 64 encoded byte array.
     *
     * @return a byte array containing the URL safe base 64 encoded data.
     */
    public static byte[] encode(
        byte[]    data)
    {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        try
        {
            encoder.encode(data, 0, data.length, bOut);
        }
        catch (Exception e)
        {
            throw new EncoderException("exception encoding URL safe base64 data: " + e.getMessage(), e);
        }
        
        return bOut.toByteArray();
    }

    /**
     * Encode the byte data writing it to the given output stream.
     *
     * @return the number of bytes produced.
     */
    public static int encode(
        byte[]                data,
        OutputStream out)
        throws IOException
    {
        return encoder.encode(data, 0, data.length, out);
    }
    
    /**
     * Decode the URL safe base 64 encoded input data - white space will be ignored.
     *
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(
        byte[]    data)
    {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        try
        {
            encoder.decode(data, 0, data.length, bOut);
        }
        catch (Exception e)
        {
            throw new DecoderException("exception decoding URL safe base64 string: " + e.getMessage(), e);
        }
        
        return bOut.toByteArray();
    }
    
    /**
     * decode the URL safe base 64 encoded byte data writing it to the given output stream,
     * whitespace characters will be ignored.
     *
     * @return the number of bytes produced.
     */
    public static int decode(
        byte[]                data,
        OutputStream out)
        throws IOException
    {
        return encoder.decode(data, 0, data.length, out);
    }
    
    /**
     * decode the URL safe base 64 encoded String data - whitespace will be ignored.
     *
     * @return a byte array representing the decoded data.
     */
    public static byte[] decode(
        String data)
    {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        try
        {
            encoder.decode(data, bOut);
        }
        catch (Exception e)
        {
            throw new DecoderException("exception decoding URL safe base64 string: " + e.getMessage(), e);
        }
        
        return bOut.toByteArray();
    }
    
    /**
     * Decode the URL safe base 64 encoded String data writing it to the given output stream,
     * whitespace characters will be ignored.
     *
     * @return the number of bytes produced.
     */
    public static int decode(
        String data,
        OutputStream out)
        throws IOException
    {
        return encoder.decode(data, out);
    }
}