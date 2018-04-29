/**
 * 
 */
package com.kail.kws.type;

import com.kail.kws.data.Request;
import com.kail.kws.data.Response;

/**
 * @author kaiyuan.liang
 *
 */
public interface IRequestHandler {
	Response GET(Request request);
}
