package com.nycreates.student.model.dto;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ReflectClassDto {

  private final Class<?> type;
  private List<ReflectFieldDto<?>> fields;

  public ReflectClassDto(final Class<?> type){
    super();
    this.type = type;
    this.fields = new ArrayList<>();
  }

  public List<ReflectFieldDto<?>> getFieldsByNames(String[] names){
    if(names == null){
      return Collections.emptyList();
    }
    final Set<String> namesToFilter = Sets.newHashSet(names);
    return fields.stream()
        .filter(f -> namesToFilter.contains(f.getField()))
        .collect(Collectors.toList());
  }

  public void removeFieldsByNames(String[] names){
    fields.removeAll(getFieldsByNames(names));
  }
}
