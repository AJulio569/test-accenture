package co.com.bancolombia.mongo.mapper;

import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.mongo.model.FranchiseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFranchiseMapper {
    Franchise toDomain(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise domain);
}
