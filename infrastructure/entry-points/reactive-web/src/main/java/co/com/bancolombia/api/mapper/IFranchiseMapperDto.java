package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.model.request.BranchRequest;
import co.com.bancolombia.api.model.request.FranchiseRequest;
import co.com.bancolombia.api.model.request.ProductRequest;
import co.com.bancolombia.api.model.response.BranchResponse;
import co.com.bancolombia.api.model.response.FranchiseResponse;
import co.com.bancolombia.api.model.response.ProductResponse;
import co.com.bancolombia.model.franchise.model.Branch;
import co.com.bancolombia.model.franchise.model.Franchise;
import co.com.bancolombia.model.franchise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface IFranchiseMapperDto {
    Franchise toDomain(FranchiseRequest request);
    Branch toDomain(BranchRequest request);
    Product toDomain(ProductRequest request);

    FranchiseResponse toResponse(Franchise domain);
    BranchResponse toResponse(Branch branch);
    ProductResponse toResponse(Product product);

    List<Branch> toBranchDomainList(List<BranchRequest> branches);
    List<Product> toProductDomainList(List<ProductRequest> products);

}
