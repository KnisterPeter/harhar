package com.adviser.shared.actions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component("Files")
public class Files extends Base {

  private final static int HTTP_OK = 200;

  private final static int HTTP_FILENOTFOUND = 404;

  private final static int HTTP_REDIRECT = 302;

  private final static int HTTP_IFNONMATCH = 304;

  public static class File {

    private int responseCode = Files.HTTP_OK;

    private String extention;

    private String etag;

    private byte[] body;

    private String location;

    private Map<String, String> ext2mime;

    public String getMime() {
      if (extention == null) {
        extention = "html";
      }
      if (ext2mime == null) {
        ext2mime = new HashMap<String, String>();
        ext2mime.put("js", "text/javascript; charset=UTF-8");
        ext2mime.put("css", "text/css; charset=UTF-8");
        ext2mime.put("html", "text/html; charset=UTF-8");
        ext2mime.put("gif", "image/gif");
        ext2mime.put("png", "image/png");
      }
      return ext2mime.get(extention);
    }

    public File update(final Message out) {
      out.setHeader(Exchange.HTTP_RESPONSE_CODE, responseCode);
      out.setHeader("content-type", this.getMime());
      if (location != null) {
        out.setHeader("location", location);
      }
      out.setBody(body);
      return this;
    }

    /**
     * @return the responseCode
     */
    public final int getResponseCode() {
      return responseCode;
    }

    /**
     * @param responseCode
     *          the responseCode to set
     */
    public final void setResponseCode(final int responseCode) {
      this.responseCode = responseCode;
    }

    /**
     * @return the extention
     */
    public final String getExtention() {
      return extention;
    }

    /**
     * @param extention
     *          the extention to set
     */
    public final void setExtention(final String extention) {
      this.extention = extention;
    }

    /**
     * @return the etag
     */
    public final String getEtag() {
      return etag;
    }

    /**
     * @param etag
     *          the etag to set
     */
    public final void setEtag(final String etag) {
      this.etag = etag;
    }

    /**
     * @return the body
     */
    public final byte[] getBody() {
      return body;
    }

    /**
     * @param body
     *          the body to set
     */
    public final void setBody(final byte[] body) {
      this.body = body;
    }

    /**
     * @return the location
     */
    public final String getLocation() {
      return location;
    }

    /**
     * @param location
     *          the location to set
     */
    public final void setLocation(final String location) {
      this.location = location;
    }

    /**
     * @return the ext2mime
     */
    public final Map<String, String> getExt2mime() {
      return ext2mime;
    }

    /**
     * @param ext2mime
     *          the ext2mime to set
     */
    public final void setExt2mime(final Map<String, String> ext2mime) {
      this.ext2mime = ext2mime;
    }

  }

  public File readFile(final File inFile, final Exchange exchange) {
    String path = exchange.getIn().getHeader("CamelHttpUri", String.class);
    String filePath = path;
    if (path == null || path.length() == 0) {
      filePath = "/index.html";
    } else if (path.substring(path.length() - 1).equals("/")) {
      filePath += "index.html";
    }
    // filePath = filePath.substring(1); //skip slash
    // LOGGER.error(path);
    synchronized (this) {
      File file = inFile;
      if (file != null) {
        return file;
      }
      file = new File();
      byte[] data;
      try {
        // System.out.println("XXXX:"+filePath);
        InputStream is = Files.class.getResourceAsStream(filePath);
        if (is == null) {
          file.setResponseCode(Files.HTTP_FILENOTFOUND);
        } else {
          data = IOUtils.toByteArray(is);
          file.setEtag(DigestUtils.md5Hex(data));
          file.setExtention(FilenameUtils.getExtension(path));
          if (file.getEtag().equals(exchange.getIn().getHeader("If-None-Match"))) {
            file.setResponseCode(Files.HTTP_IFNONMATCH);
          } else {
            file.setBody(data);
          }
        }
      } catch (Exception e) {
        if (!path.substring(path.length() - 1).equals("/") && NullPointerException.class.isInstance(e)) {
          file.setResponseCode(Files.HTTP_REDIRECT);
          file.setLocation(path + "/");
        } else {
          file.setResponseCode(Files.HTTP_FILENOTFOUND);
        }
        file.update(exchange.getOut());
        return file;
      }

      return file.update(exchange.getOut());
    }
  }

  public void page(final Exchange exchange) {
    this.readFile(null, exchange);
  }

}
