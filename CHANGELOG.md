# Changelog

## 1.1.1:
    - Corrigido bug de ArrayIndexOutOfBounds no `BoletoBancarioTextWatcher` após apagar em lote
    - Adicionado teste de regressão para o caso acima na JVM que será executado no Travis

## 1.1.0:
    - Refatorados testes instrumentados
    - Adicionado construtor para máscara genérica

## 1.0.0:
    - Removida necessidade de ter um validador/evento de validação na criação de Watchers

## 0.1.0:
    - Ajustes no código antes da versão 1.0

## 0.0.9:
    - Correção final para o formatador/validador de boleto. Tanto normal quanto tributo.
## 0.0.8:
    - Adição de formatdor/validador de CEP
    - Correção de formatdor monetário ao rotacionar a tela
## 0.0.7:
    - Correção de validador de boleto quando setando o código de boleto inteiro (caso de uso: recebendo de um leitor de imagens)
## 0.0.6:
    - Watcher de CPF/CNPJ simultâneos
## 0.0.5:
    - Release no JCenter
## 0.0.4:
    - Watcher para valor monetário no padão Real (vírgula para casas de milhar e ponto para casas decimais)
    - Re-estruturação do projeto para gerar os binários na pasta correta do Bintray
## 0.0.3:
    - Série de ajustes para Travis e JCenter (ainda não publicado)
## 0.0.2:
    - Formatadores de telefone
## 0.0.1:
    - Release inicial