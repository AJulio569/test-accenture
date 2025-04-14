package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.model.request.FranchiseRequest;
import co.com.bancolombia.api.model.response.FranchiseResponse;
import co.com.bancolombia.model.franchise.model.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface IFranchiseMapperDto {
    Franchise toDomain(FranchiseRequest request);
    FranchiseResponse toResponse(Franchise domain);
}
