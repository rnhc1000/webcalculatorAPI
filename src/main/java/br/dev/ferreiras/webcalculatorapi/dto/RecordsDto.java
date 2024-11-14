package br.dev.ferreiras.webcalculatorapi.dto;

import java.util.List;

public record RecordsDto(List<RecordItemsDto> records,
                         int page,
                         int size,
                         int totalPages,
                         long totalRecords,
                         boolean active){
}