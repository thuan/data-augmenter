package com.itegra.auditcom.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class NotaFiscalEntradaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idNfe;

    private Integer crt;

    private String ufEmitente;

    private String ufDestinatario;

    private Double valorItem;

    private Double valorIPI;

    private Double valorFrete;

    private Double valorSeguro;

    private Double valorOutros;

    private String cnpjEmitente;

    private String cnpjDestinatario;

    private String categoria;

    private String normaExecucao;

    private ProdutoEntradaDTO produtoEntrada;

    private String augment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNfe() {
        return idNfe;
    }

    public void setIdNfe(String idNfe) {
        this.idNfe = idNfe;
    }

    public Integer getCrt() {
        return crt;
    }

    public void setCrt(Integer crt) {
        this.crt = crt;
    }

    public String getUfEmitente() {
        return ufEmitente;
    }

    public void setUfEmitente(String ufEmitente) {
        this.ufEmitente = ufEmitente;
    }

    public String getUfDestinatario() {
        return ufDestinatario;
    }

    public void setUfDestinatario(String ufDestinatario) {
        this.ufDestinatario = ufDestinatario;
    }

    public Double getValorItem() {
        return valorItem;
    }

    public void setValorItem(Double valorItem) {
        this.valorItem = valorItem;
    }

    public Double getValorIPI() {
        return valorIPI;
    }

    public void setValorIPI(Double valorIPI) {
        this.valorIPI = valorIPI;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(Double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public Double getValorOutros() {
        return valorOutros;
    }

    public void setValorOutros(Double valorOutros) {
        this.valorOutros = valorOutros;
    }

    public String getCnpjEmitente() {
        return cnpjEmitente;
    }

    public void setCnpjEmitente(String cnpjEmitente) {
        this.cnpjEmitente = cnpjEmitente;
    }

    public String getCnpjDestinatario() {
        return cnpjDestinatario;
    }

    public void setCnpjDestinatario(String cnpjDestinatario) {
        this.cnpjDestinatario = cnpjDestinatario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNormaExecucao() {
        return normaExecucao;
    }

    public void setNormaExecucao(String normaExecucao) {
        this.normaExecucao = normaExecucao;
    }

    public ProdutoEntradaDTO getProdutoEntrada() {
        return produtoEntrada;
    }

    public void setProdutoEntrada(ProdutoEntradaDTO produtoEntrada) {
        this.produtoEntrada = produtoEntrada;
    }

    public String getAugment() {
        return augment;
    }

    public void setAugment(String augment) {
        this.augment = augment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotaFiscalEntradaDTO)) {
            return false;
        }

        NotaFiscalEntradaDTO notaFiscalEntradaDTO = (NotaFiscalEntradaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notaFiscalEntradaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotaFiscalEntradaDTO{" +
            "id=" + getId() +
            ", idNfe='" + getIdNfe() + "'" +
            ", crt=" + getCrt() +
            ", ufEmitente='" + getUfEmitente() + "'" +
            ", ufDestinatario='" + getUfDestinatario() + "'" +
            ", valorItem=" + getValorItem() +
            ", valorIPI=" + getValorIPI() +
            ", valorFrete=" + getValorFrete() +
            ", valorSeguro=" + getValorSeguro() +
            ", valorOutros=" + getValorOutros() +
            ", cnpjEmitente='" + getCnpjEmitente() + "'" +
            ", cnpjDestinatario='" + getCnpjDestinatario() + "'" +
            ", categoria='" + getCategoria() + "'" +
            ", normaExecucao='" + getNormaExecucao() + "'" +
            ", produtoEntrada=" + getProdutoEntrada() +
            ", augment=" + getAugment() +
            "}";
    }
}
