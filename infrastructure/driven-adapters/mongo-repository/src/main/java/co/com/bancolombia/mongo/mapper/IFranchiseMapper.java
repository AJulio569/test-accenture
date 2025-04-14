package co.com.bancolombia.mongo.mapper;

import co.com.bancolombia.model.franchise.model.Branch;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.model.franchise.model.Product;
import co.com.bancolombia.mongo.model.BranchEntity;
import co.com.bancolombia.mongo.model.FranchiseEntity;
import co.com.bancolombia.mongo.model.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFranchiseMapper {
    Franchise toDomain(FranchiseEntity entity);
    FranchiseEntity toEntity(Franchise domain);

    Branch toDomain(BranchEntity entity);
    BranchEntity toEntity(Branch domain);

    Product toDomain(ProductEntity entity);
    ProductEntity toEntity(Product domain);
}
