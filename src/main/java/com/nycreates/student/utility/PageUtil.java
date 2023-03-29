package com.nycreates.student.utility;

import javax.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@UtilityClass
public class PageUtil {

  private static final int DEFAULT_SIZE = 20;

  public static PageRequest createPageRequest(final Integer page, final Integer size){
    return createPageRequest(page, size, DEFAULT_SIZE);
  }

  public static PageRequest createPageRequest(
      final Integer page,
      final Integer size,
      final Integer defaultSize
  ){
    final int pg = page != null && page > 0 ? page - 1 : 0;
    final int sz = size != null && size > 0 ? size : defaultSize;
    return PageRequest.of(pg, sz);
  }

  public static void updatePageHeaders(
      final HttpServletResponse httpResponse,
      final Page<?> page,
      final PageRequest pageRequest
  ){
    if(httpResponse == null){
      return;
    }
    httpResponse.setIntHeader("page-count", page.getTotalPages());
    httpResponse.setIntHeader("page-number", pageRequest.getPageNumber() + 1);
    httpResponse.setIntHeader("page-size", pageRequest.getPageSize());
  }
}
