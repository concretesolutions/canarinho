# Android Canarinho

[![Build](https://travis-ci.org/concretesolutions/canarinho.svg?branch=master)](https://travis-ci.org/concretesolutions/canarinho)


Esta biblioteca é um conjunto de utilitários para trabalhar com padrões brasileiros no Android. Inspirado em: https://github.com/caelum/caelum-stella.

O foco aqui é o Android. Portanto, não é compatível com aplicações Java puras.

Entre os padrões implementados temos:

- Formatador e validador de CPF
- Formatador e validador de CNPJ
- Formatador e validador de boleto bancário (e linha digitável)
- Formatador e validador de CEP
- Formatador de telefone
- Formatador de valores financeiros no padrão Real (vírgula para milhares e ponto para decimais com duas casas)

Estes são utilizados para implementar `TextWatcher`s que formatam e validam a digitação do usuário.

## Exemplo de uso:

### Validar um CPF

```java
if (Validador.CPF.ehValido(cpf))
    Toast.makeText(context, "Válido!", Toast.LENGTH_SHORT).show();
else
    Toast.makeText(context, "Inválido!", Toast.LENGTH_SHORT).show();
```

### Formatar um CPF

```java
String cpfFormatado = Formatador.CPF.formata(usuario.getCpf());
```

### Formatar um EditText para CPF sem validação

```java
cpfEditText.addTextChangedListener(new MascaraNumericaTextWatcher("###.###.###-##"));
```

### Formatar um EditText para CPF com validação

```java
cpfEditText.addTextChangedListener(new MascaraNumericaTextWatcher.Builder()
                                        .paraMascara("###.###.###-##")
                                        .comCallbackDeValidacao(new SampleEventoDeValidacao(context))
                                        .comValidador(Validador.CPF)
                                        .build());
```

## Callback de validação

Os `TextWatcher`s possuem a possibilidade de avisar o usuário conforme ele está digitando sobre algum erro de campo.
Para isso, usamos um `EventoDeValidacao` que possui os seguintes callbacks:

- `void invalido(String valorAtual, String mensagem)`: chamado quando o valor está inválido
- `void parcialmenteValido(String valorAtual)`: chamado quando o valor ainda não está completo e também não está inválido
- `void totalmenteValido(String valorAtual)`: chamado quando o valor está completo e válido

Veja exemplos de implementação no sample.

## Changelog

Ver [CHANGELOG.md](CHANGELOG.md)

## Arquitetura

- `Formatador`: Formata, desformata e verifica se um valor está formatado e se pode ser formatado. Opera com valores completos.
- `Validador`: Valida de duas formas: absoluta (true ou false) e atualizando um objeto de validação (`ResultadoParcial`).
- Watchers: implementações de `TextWatcher`s para formatação e validação contínua (conforme a digitação do usuário.

Para exemplos, verifique os testes na pasta sample.

## Gradle

`compile 'br.com.concretesolutions:canarinho:1.1.0'`

## ATENÇÃO

Este projeto é desenvolvido de boa vontade e com o intuito de ajudar. No entanto, todo o desenvolvimento é feito SEM GARANTIAS.

## LICENÇA

Este projeto é disponibilizado sob a licença Apache vesão 2.0. Ver declaração no arquivo LICENSE.txt
