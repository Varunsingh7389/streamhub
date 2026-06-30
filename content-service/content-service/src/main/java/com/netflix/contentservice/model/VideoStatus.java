package com.netflix.contentservice.model;

/*
* Status
* Tacks the video processing lifecycle
*
* Flow
* pending-> uploaded-> encoding->encoded-> Ready
*                              -> Failed
 */
public enum VideoStatus {
    PENDING, // movie added but not uploaded
    UPLOADED, //raw video uploaded at S3
    ENCODING, // FFmpeg isencoding the video
    ENCODED, //Encoding complete
    READY, //HLS palylist ready, can be streamed
    FAILED //Encoding failed

}
