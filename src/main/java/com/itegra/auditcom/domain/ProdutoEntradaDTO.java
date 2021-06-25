package com.itegra.auditcom.domain;

import java.io.Serializable;
import java.util.Objects;

public class ProdutoEntradaDTO implements Serializable {

    private Long id;

    private Double percentualICMS;

    private Integer cfop;

    private String cst;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentualICMS() {
        return percentualICMS;
    }

    public void setPercentualICMS(Double percentualICMS) {
        this.percentualICMS = percentualICMS;
    }

    public Integer getCfop() {
        return cfop;
    }

    public void setCfop(Integer cfop) {
        this.cfop = cfop;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdutoEntradaDTO)) {
            return false;
        }

        ProdutoEntradaDTO produtoEntradaDTO = (ProdutoEntradaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produtoEntradaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdutoEntradaDTO{" +
            "id=" + getId() +
            ", percentualICMS=" + getPercentualICMS() +
            ", cfop=" + getCfop() +
            ", cst='" + getCst() + "'" +
            "}";
    }
}
