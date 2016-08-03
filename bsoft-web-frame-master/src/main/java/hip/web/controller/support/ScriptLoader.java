package hip.web.controller.support;

import hip.web.controller.FileOutputMVCConroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ctd.resource.ResourceCenter;
import ctd.util.ServletUtils;
import groovy.json.StringEscapeUtils;

@Controller("mvcScriptLoader")
public class ScriptLoader extends FileOutputMVCConroller {
	private static final Logger logger = LoggerFactory.getLogger(ScriptLoader.class);

	@RequestMapping(value = "script/{clz}.js", method = RequestMethod.GET)
	public void loadScript(@PathVariable String clz, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (clz.indexOf(",") < 0) {
				Resource r = loadModWithClassName(clz);
				long lastModi = r.lastModified();
				long expiresSeconds = getFileExpires(clz);
				if (!ServletUtils.checkAndSetExpiresHeaders(request, response, lastModi, expiresSeconds)) {
					return;
				}
				response.setContentType(ServletUtils.JS_TYPE);
				boolean gzip = ServletUtils.isAcceptGzip(request);
				textFileOutput(response, r, gzip);
			} else {

				String[] classes = clz.split(",");
				long lastModi = 0l;
				long expiresSeconds = getDefaultExpires();
				int n = classes.length;
				Resource[] rs = new Resource[classes.length];
				for (int i = 0; i < n; i++) {
					clz = classes[i];
					Resource r = loadModWithClassName(clz);
					rs[i] = r;
					lastModi = Math.max(lastModi, r.lastModified());
					if (i == 0) {
						expiresSeconds = getFileExpires(clz);
					} else {
						expiresSeconds = Math.min(expiresSeconds, getFileExpires(clz));
					}
				}
				if (!ServletUtils.checkAndSetExpiresHeaders(request, response, lastModi, expiresSeconds)) {
					return;
				}
				response.setContentType(ServletUtils.JS_TYPE);
				response.setCharacterEncoding(ServletUtils.DEFAULT_ENCODING);
				boolean gzip = ServletUtils.isAcceptGzip(request);
				textFileOutput(response, rs, gzip);
			}

		} catch (FileNotFoundException e) {
			logger.error("scriptFile[" + clz + "] not found.");
			ServletUtils.setNoCacheHeader(response);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException e) {
			logger.error("scriptFile[" + clz + "] IOException", e);
			ServletUtils.setNoCacheHeader(response);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private Resource loadModWithClassName(String clz) throws IOException {
		String path1 = clz.replaceAll("\\.", "/");
		Resource mres = ResourceCenter.loadOrNull(path1 + ".mod");
		Resource cres = ResourceCenter.loadOrNull(path1 + ".css");
		Resource hres = ResourceCenter.loadOrNull(path1 + ".html");
		Resource jres = ResourceCenter.loadOrNull(path1 + ".js");
		if (cres != null || hres != null) {
			long lastM = 0;
			if (cres != null) {
				long l = cres.getFile().lastModified();
				if (l > lastM)
					lastM = l;
			}
			if (hres != null) {
				long l = hres.getFile().lastModified();
				if (l > lastM)
					lastM = l;
			}
			if (jres != null) {
				long l = jres.getFile().lastModified();
				if (l > lastM)
					lastM = l;
			}
			if (mres != null && lastM < mres.getFile().lastModified())
				return mres;
			else
				return createModFile(clz);
		} else {
			if (jres == null)
				jres = ResourceCenter.loadOrNull(path1 + ".min.js");
			if (jres == null)
				throw new FileNotFoundException();
			return jres;
		}
	}

	// 对 css，html，js 文件进行编译，生成.mod文件
	private Resource createModFile(String clz) throws IOException {
		System.out.println("重新构建：" + clz);
		String path1 = clz.replaceAll("\\.", "/");
		File fn = null;
		StringBuffer sBuffer = new StringBuffer();
		Resource jres = ResourceCenter.loadOrNull(path1 + ".js");
		if (jres != null) {
			sBuffer.append(appendResByLines(true, jres));
			sBuffer.append("\n");
			fn = jres.getFile();
		}
		sBuffer.append("$package(\"" + clz + "\")");
		sBuffer.append("\n");
		Resource cres = ResourceCenter.loadOrNull(path1 + ".css");
		if (cres != null) {
			sBuffer.append("" + clz + ".prototype._css=\"");
			String css = StringEscapeUtils.escapeJava(appendResByLines(false, cres).toString());
			sBuffer.append(css);
			sBuffer.append("\";");
			sBuffer.append("\n");
			fn = cres.getFile();
		}
		Resource hres = ResourceCenter.loadOrNull(path1 + ".html");
		if (hres != null) {
			sBuffer.append("" + clz + ".prototype._html=\"");
			String html = StringEscapeUtils.escapeJava(appendResByLines(false, hres).toString());
			sBuffer.append(html);
			sBuffer.append("\";");
			sBuffer.append("\n");
			fn = hres.getFile();
		}

		if (fn != null) {
			int i = fn.getName().indexOf(".");
			String name = fn.getName().substring(0, i);
			string2File(sBuffer.toString(), fn.getParent() + File.separatorChar + name + ".mod");
		}

		return ResourceCenter.loadOrNull(path1 + ".mod");
	}

	// 保存字符串到文件中
	private boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	private StringBuffer appendResByLines(boolean newline, Resource resource) {
		StringBuffer sBuffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(resource.getFile()));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				// System.out.println("line : " + tempString);
				sBuffer.append(tempString);
				if (newline)
					sBuffer.append("\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sBuffer;
	}

}
