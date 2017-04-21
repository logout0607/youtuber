package com.todaywork.cmm;

import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 파일 전송 관련 Util
 * Created by 권 오빈 on 2016. 6. 16..
 */
public class FileSenderUtil {

	private static final int DEFAULT_BUFFER_SIZE = 20480; // 20KB
	private static final long DEFAULT_EXPIRE_TIME = 604800000L; // 60 * 60 * 24 * 7 * 1000 (1Week)
	private static final String MULTIPART_BOUNDARY = "MUTIPART_BYTERANGES";

	HttpServletRequest request;
	HttpServletResponse response;
	File file;
	MediaType contentType;
	String originName;

	public static FileSenderUtil fromFile(File file) { return new FileSenderUtil().setFile(file); }

	private FileSenderUtil setFile(File file){
		this.file = file;
		return this;
	}

	public FileSenderUtil with(HttpServletRequest request){
		this.request = request;
		return this;
	}

	public FileSenderUtil with(HttpServletResponse response){
		this.response = response;
		return this;
	}

	public FileSenderUtil with(MediaType contentType){
		this.contentType = contentType;
		return this;
	}

	public FileSenderUtil with(String originName){
		this.originName = originName;
		return this;
	}

	public void serveResource() throws Exception {
		if (request == null || response == null) {
			return;
		}

		Long length = file.length();
		String fileNm = file.getName();

		long lastModified = file.lastModified();

		// LastModified
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");

		Range full = new Range(0, length - 1, length);
		List<Range> ranges = new ArrayList<>();

		// Validate and process Range and If-Range headers.
		String range = request.getHeader("Range");
		if (range != null) {

			// Range header should match format "bytes=n-n,n-n,n-n...". If not, then return 416.
			if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
				response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}

			String ifRange = request.getHeader("If-Range");
			if (ifRange != null && !ifRange.equals(fileNm)) {
				try {
					long ifRangeTime = request.getDateHeader("If-Range"); // Throws IAE if invalid.
					if (ifRangeTime != -1) {
						ranges.add(full);
					}
				} catch (IllegalArgumentException ignore) {
					ranges.add(full);
				}
			}

			// If any valid If-Range header, then process each part of byte range.
			if (ranges.isEmpty()) {
				for (String part : range.substring(6).split(",")) {
					// Assuming a file with length of 100, the following examples returns bytes at:
					// 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
					long start = Range.sublong(part, 0, part.indexOf("-"));
					long end = Range.sublong(part, part.indexOf("-") + 1, part.length());

					if (start == -1) {
						start = length - end;
						end = length - 1;
					} else if (end == -1 || end > length - 1) {
						end = length - 1;
					}

					// Check if Range is syntactically valid. If not, then return 416.
					if (start > end) {
						response.setHeader("Content-Range", "bytes */" + length); // Required in 416.
						response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
						return;
					}

					// Add range.
					ranges.add(new Range(start, end, length));
				}
			}
		}

		String disposition = "inline";
		String cntType = contentType.getType();

		// Initialize response.
		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setHeader("Content-Type", cntType);
		response.setHeader("Content-Disposition", disposition + ";filename=\"" + fileNm + "\"");

		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("ETag", fileNm);
		response.setDateHeader("Last-Modified", lastModified);
		response.setDateHeader("Expires", System.currentTimeMillis() + DEFAULT_EXPIRE_TIME);

		// Send requested file (part(s)) to client ------------------------------------------------

		// Prepare streams.
		try (InputStream input = new BufferedInputStream(new FileInputStream(file));
			 OutputStream output = response.getOutputStream()) {

			if (ranges.isEmpty() || ranges.get(0) == full) {

				// Return full file.
				response.setContentType(cntType);
				response.setHeader("Content-Range", "bytes " + full.start + "-" + full.end + "/" + full.total);
				response.setHeader("Content-Length", String.valueOf(full.length));
				Range.copy(input, output, length, full.start, full.length);

			} else if (ranges.size() == 1) {

				// Return single part of file.
				Range r = ranges.get(0);
				response.setContentType(cntType);
				response.setHeader("Content-Range", "bytes " + r.start + "-" + r.end + "/" + r.total);
				response.setHeader("Content-Length", String.valueOf(r.length));
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

				// Copy single part range.
				Range.copy(input, output, length, r.start, r.length);

			} else {

				// Return multiple parts of file.
				response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

				// Cast back to ServletOutputStream to get the easy println methods.
				ServletOutputStream sos = (ServletOutputStream) output;

				// Copy multi part range.
				for (Range r : ranges) {
					// Add multipart boundary and header fields for every range.
					sos.println();
					sos.println("--" + MULTIPART_BOUNDARY);
					sos.println("Content-Type: " + cntType);
					sos.println("Content-Range: bytes " + r.start + "-" + r.end + "/" + r.total);

					// Copy single part range of multi part range.
					Range.copy(input, output, length, r.start, r.length);
				}

				// End with multipart boundary.
				sos.println();
				sos.println("--" + MULTIPART_BOUNDARY + "--");
			}
		}
	}

	private static class Range {
		long start;
		long end;
		long length;
		long total;

		/**
		 * Construct a byte range.
		 *
		 * @param start
		 *            Start of the byte range.
		 * @param end
		 *            End of the byte range.
		 * @param total
		 *            Total length of the byte source.
		 */
		public Range(long start, long end, long total) {
			this.start = start;
			this.end = end;
			this.length = end - start + 1;
			this.total = total;
		}

		public static long sublong(String value, int beginIndex, int endIndex) {
			String substring = value.substring(beginIndex, endIndex);
			return (substring.length() > 0) ? Long.parseLong(substring) : -1;
		}

		private static void copy(InputStream input, OutputStream output,
								 long inputSize, long start, long length) throws IOException {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int read;

			if (inputSize == length) {
				// Write full range.
				while ((read = input.read(buffer)) > 0) {
					output.write(buffer, 0, read);
					output.flush();
				}
			} else {
				input.skip(start);
				long toRead = length;

				while ((read = input.read(buffer)) > 0) {
					if ((toRead -= read) > 0) {
						output.write(buffer, 0, read);
						output.flush();
					} else {
						output.write(buffer, 0, (int) toRead + read);
						output.flush();
						break;
					}
				}
			}
		}
	}

	private static class HttpUtils {

		/**
		 * Returns true if the given accept header accepts the given value.
		 *
		 * @param acceptHeader
		 *            The accept header.
		 * @param toAccept
		 *            The value to be accepted.
		 * @return True if the given accept header accepts the given value.
		 */
		public static boolean accepts(String acceptHeader, String toAccept) {
			String[] acceptValues = acceptHeader.split("\\s*(,|;)\\s*");
			Arrays.sort(acceptValues);

			return Arrays.binarySearch(acceptValues, toAccept) > -1
				|| Arrays.binarySearch(acceptValues,
				toAccept.replaceAll("/.*$", "/*")) > -1
				|| Arrays.binarySearch(acceptValues, "*/*") > -1;
		}

		/**
		 * Returns true if the given match header matches the given value.
		 *
		 * @param matchHeader
		 *            The match header.
		 * @param toMatch
		 *            The value to be matched.
		 * @return True if the given match header matches the given value.
		 */
		public static boolean matches(String matchHeader, String toMatch) {
			String[] matchValues = matchHeader.split("\\s*,\\s*");
			Arrays.sort(matchValues);
			return Arrays.binarySearch(matchValues, toMatch) > -1
				|| Arrays.binarySearch(matchValues, "*") > -1;
		}
	}
}
