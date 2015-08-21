# Android Canarinho [![Build Status](https://travis-ci.org/cs-victor-nascimento/canarinho.svg?branch=master)](https://travis-ci.org/cs-victor-nascimento/canarinho)

Esta biblioteca é um conjunto de utilitários para trabalhar com padrões brasileiros no Android. Inspirado em: https://github.com/caelum/caelum-stella.

O foco aqui é o Android. Portanto, não é compatível com aplicações Java puras.

Entre os padrões implementados temos:

- Formatador e validador de CPF
- Formatador e validador de CNPJ
- Formatador e validador de boleto bancário (e linha digitável)
- Formatador de telefone
- Formatador de valores financeiros no padrão Real (vírgula para milhares e ponto para decimais com duas casas)

Estes são utilizados para implementar `TextWatcher`s que formatam e validam a digitação do usuário.

## Changelog

Ver [CHANGELOG.md](CHANGELOG.md)

## Arquitetura

- `Formatador`: Formata, desformata e verifica se um valor está formatado e se pode ser formatado. Opera com valores completos.
- `Validador`: Valida de duas formas: absoluta (true ou false) e atualizando um objeto de validação (`ResultadoParcial`).
- Watchers: implementações de `TextWatcher`s para formatação e validação contínua (conforme a digitação do usuário.

Para exemplos, verifique os testes na pasta sample.

## Backlog para 1.0.0

- [x] Incluir Checkstyle, PMD e FindBugs (com refinamento se não todos endoidamos).
- [x] Analisar a viabilidade de rodar os testes de JVM no Travis.
- [ ] Incluir formatador e watcher para CEP.
- [ ] Melhorar a forma de expor utilitários da biblioteca para criação de Watchers.
- [ ] Estudar formas de manter a posição do cursor quando clicar no meio da String.
- [ ] Incluir validadores e formatadores para inscrições estaduais.
- [ ] Publicar o Sample.
- [ ] Incluir mais testes de JVM (Watchers estão apenas como testes instrumentados e dependem da interface).
- [ ] Criar uma página gh-pages que tenha um link para Javadoc.
    - [ ] Criar GIFs do uso.
    - [ ] Terminar de documentar com Javadoc e publicar.
    - [ ] Aumentar a documentação do projeto sobre o uso.]
    - [ ] Incluir documentação de como testar.
    - [ ] Incluir documentação sobre a configuração de ProGuard.
    - [ ] Incluir uma tradução para o inglês das interfaces públicas apenas na documentação.

## ATENÇÃO

Este projeto é desenvolvido de boa vontade e com o intuito de ajudar. No entanto, todo o desenvolvimento é feito SEM GARANTIAS.

## LICENÇA

Este projeto é disponibilizado sob a licença Apache vesão 2.0. Ver declaração no arquivo LICENSE.txt