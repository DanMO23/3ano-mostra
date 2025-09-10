package com.backend.pi.backend.utils;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.backend.pi.backend.model.*;
import com.backend.pi.backend.repository.*;

@Component
public class DataLoader implements CommandLineRunner {

   private final ArcoRepository arcoRepository;
   private final RelatorioTurmaRepository relatorioTurmaRepository;
   private final RelatorioAlunoRepository relatorioAlunoRepository;

   public DataLoader(ArcoRepository arcoRepository, CartaRepository cartaRepository,
         FeedbackRepository feedbackRepository, RelatorioTurmaRepository relatorioTurmaRepository,
         RelatorioAlunoRepository relatorioAlunoRepository) {
      this.arcoRepository = arcoRepository;
      this.relatorioAlunoRepository = relatorioAlunoRepository;
      this.relatorioTurmaRepository = relatorioTurmaRepository;
   }

   @Override
   public void run(String... args) {
      if (arcoRepository.count() == 0) {
         // Criando o primeiro arco
         Arco arco1 = new Arco();
         arco1.setNome("Game Perigoso");
         arco1.setCategoria(CategoriaArco.Game_Perigoso);
         arco1.setBackgroundArco("https://i.imgur.com/GQ3GxrP.png");

         // Criando cartas para o primeiro arco
         List<Carta> cartasArco1 = List.of(
               createCarta("Convite para o Desafio",
                     "Um vídeo estranho aparece na sua tela sobre uma nova comunidade online com desafios e te convida a participar.",
                     NivelCarta.LEVE, TipoCarta.CONTATO, arco1,
                     "https://i.imgur.com/aQ6gu1a.png",
                     "Carta 1",
                     "Assistir ao vídeo",
                     "Recebe um convite para completar o primeiro desafio.",
                     2,
                     "Ignorar o vídeo",
                     "O jogo não progride. O jogador evitou abrir o conteúdo que envolve",
                     6,
                     LadoCorreto.DIREITA),
               createCarta("Primeiro Desafio",
                     "Após aceitar o convite, você recebe o primeiro desafio, para trocar sua foto de perfil por um tigre",
                     NivelCarta.LEVE, TipoCarta.CONDUTA, arco1,
                     "https://i.imgur.com/Qb7oV5J.png",
                     "Carta 2",
                     "Completar o desafio e trocar sua foto de perfil.",
                     "Você recebe um novo desafio.",
                     4,
                     "Não completar",
                     "Recebe ameaças de que algo ruim acontecerá.",
                     3,
                     LadoCorreto.DIREITA),
               createCarta("Ameaças do Jogo",
                     "Ao recusar o desafio, o dono do grupo te ameaça.",
                     NivelCarta.PESADO, TipoCarta.CONTATO, arco1,
                     "https://i.imgur.com/vFrBQFC.png",
                     "Carta 3",
                     "Contar para um adulto",
                     "O adulto ajuda a denunciar.",
                     -1,
                     "Ignorar as ameaças",
                     "Redução de confiança e bem-estar.",
                     -1,
                     LadoCorreto.ESQUERDA),
               createCarta("Desafios Avançados",
                     "Ao completar o desafio, você recebe o desafio de sair de casa sem avisar aos seus pais e de tirar uma foto na rua",
                     NivelCarta.LEVE, TipoCarta.CONTATO, arco1,
                     "https://i.imgur.com/Dceaydv.png",
                     "Carta 4",
                     "Parar e buscar ajuda.",
                     "Risco físico aumenta.",
                     -1,
                     "Realizar o desafio.",
                     "Segurança aumenta.",
                     5, LadoCorreto.ESQUERDA),
               createCarta("Situação Perigosa",
                     "Ao continuar no grupo de desafios, os membros te pedem para começar a passar seus dados pessoais e fotos suas",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco1,
                     "https://i.imgur.com/zHdLZdc.png",
                     "Carta 5",
                     "Continuar no jogo e passar seus dados.",
                     "Bem-estar reduzido drasticamente.",
                     -1,
                     "Denunciar o jogo para um adulto.",
                     "Segurança aumenta significativamente.",
                     -1,
                     LadoCorreto.DIREITA),
               createCarta("Um Segundo Convite",
                     "O vídeo continua aparecendo na sua tela e parece que não sairá se você não clicar.",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco1,
                     "https://i.imgur.com/SjusbuO.png",
                     "Carta 6",
                     "Assistir ao vídeo.",
                     null,
                     2,
                     "Ignorar o vídeo.",
                     null,
                     7,
                     LadoCorreto.DIREITA),
               createCarta("Sussuros no Corredor",
                     "Um tempo depois, você escuta histórias sobre pessoas que assistiram ao vídeo e descobre o perigo de que se precaveu.",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco1,
                     "https://i.imgur.com/t27E6IO.png",
                     "Carta 7", // ID da carta
                     "Conversar com seus pais sobre o que houve e pedir apoio.",
                     null, // Próxima carta (nenhuma, final positivo)
                     -1, // Texto final (nenhum, pois não há próxima carta)
                     "Ficar feliz por não ter se metido nessa.",
                     null, // Próxima carta (nenhuma, final positivo)
                     -1, // Texto final (nenhum, pois não há próxima carta)
                     LadoCorreto.DIREITA)); // Lado correto

         // Salvando cartas no arco
         arco1.setConjuntoCartas(cartasArco1);

         Feedback feedback1 = createFeedback(
               "Uau, você foi muito esperto(a)! Suas escolhas mostraram que você sabe se cuidar na internet. Você é um(a) mestre da segurança online! Continue assim, se divertindo e aprendendo coisas novas com segurança!",
               FeedbackTipo.POSITIVO, arco1);

         Feedback feedback2 = createFeedback(
               "Algumas escolhas que você fez na internet foram um pouco perigosas. A internet pode ser legal, mas precisa de cuidado! Converse com um adulto que você confia para aprender como ficar mais seguro(a) online da próxima vez. Pedir ajuda é super importante e mostra que você é forte!",
               FeedbackTipo.NEGATIVO, arco1);

         arco1.setConjuntoFeedback(List.of(feedback1, feedback2));

         // Criando o segundo arco
         Arco arco2 = new Arco();
         arco2.setNome("Fake News na Escola");
         arco2.setCategoria(CategoriaArco.Fake_News);
         arco2.setBackgroundArco("https://i.imgur.com/47gxvlm.png");

         // Criando cartas para o segundo arco
         List<Carta> cartasArco2 = List.of(
               createCarta("A Notícia do Celular Explosivo",
                     "Você recebe uma mensagem de um amigo dizendo que um novo modelo de celular explode quando usado enquanto carrega. A mensagem não indica de onde veio a informação.",
                     NivelCarta.PESADO, TipoCarta.CONTATO, arco2, // Supondo que arco2 é o arco atual
                     "https://i.imgur.com/tCehJMk.png",
                     "Carta 1", // ID da carta
                     "Compartilhar no grupo da turma sem verificar.",
                     null,
                     2, // Próxima carta
                     "Pesquisar a notícia para confirmar.",
                     null,
                     4,
                     LadoCorreto.DIREITA), // Próxima carta
               createCarta("Desinformação Espalhada",
                     "A notícia que você compartilhou chega aos professores e até aos pais. Muitos alunos começam a desconfiar dos celulares. Você percebe que ninguém mais está carregando seus dispositivos no colégio.",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco2,
                     "https://i.imgur.com/PUYw9Sl.png",
                     "Carta 2", // ID da carta
                     "Ignorar a situação.",
                     null, // Final negativo
                     -1,
                     "Admitir que você compartilhou sem verificar e tentar corrigir.",
                     null,
                     3,
                     LadoCorreto.DIREITA), // Próxima carta
               createCarta("Correção Difícil",
                     "Você decide corrigir a informação compartilhada, explicando a todos que era falsa. No entanto, alguns amigos ainda acreditam que a notícia é verdadeira.",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco2,
                     "https://i.imgur.com/GMBOrY9.png",
                     "Carta 3", // ID da carta
                     "Persistir na correção, usando provas e fontes confiáveis.",
                     null, // Final positivo
                     -1,
                     "Desistir de corrigir, achando que é inútil.",
                     null,
                     -1,
                     LadoCorreto.ESQUERDA),
               createCarta("Boas Práticas Confirmadas",
                     "Você descobre que a notícia é falsa, sendo um rumor criado por um site de fake news. Além de evitar compartilhar, você explica para os amigos no grupo como verificar informações na internet.",
                     NivelCarta.LEVE, TipoCarta.CONDUTA, arco2,
                     "https://i.imgur.com/zrtmbRl.png",
                     "Carta 4", // ID da carta
                     "Continuar alertando sobre fake news.",
                     null, // Final positivo
                     -1,
                     "Não compartilhar suas descobertas.",
                     null,
                     -1,
                     LadoCorreto.ESQUERDA));

         // Salvando cartas no arco
         arco2.setConjuntoCartas(cartasArco2);

         Feedback feedbackArco2PosFeedback = createFeedback(
               "Parabéns! Você mostrou que é um(a) detetive de notícias! Você investigou antes de compartilhar e ajudou a evitar que uma notícia falsa causasse problemas. Isso demonstra responsabilidade e inteligência! Continue sempre checando as informações antes de espalhá-las.",
               FeedbackTipo.POSITIVO, arco2);

         Feedback feedbackArco2NegFeedback = createFeedback(
               "Compartilhar notícias sem verificar pode causar confusão e até medo nas pessoas. Da próxima vez, lembre-se de pesquisar bem antes de enviar algo para os seus amigos. É importante ser um cidadão(ã) digital responsável e verificar a informação em sites confiáveis antes de compartilhar. Pedir ajuda a um adulto para entender melhor como identificar notícias falsas é uma ótima ideia!",
               FeedbackTipo.NEGATIVO, arco2);

         arco2.setConjuntoFeedback(List.of(feedbackArco2PosFeedback, feedbackArco2NegFeedback));

         // Criando o segundo arco
         Arco arco3 = new Arco();
         arco3.setNome("Influenciadores e Riscos Online");
         arco3.setCategoria(CategoriaArco.Riscos_Online);
         arco3.setBackgroundArco("https://i.imgur.com/cJt9nBZ.jpeg");

         // Criando cartas para o terceiro arco
         List<Carta> cartasArco3 = List.of(
               createCarta("Convite do Streamer Favorito",
                     "Um streamer muito famoso convida você, por mensagem privada, a participar de um evento exclusivo. Ele pede algumas informações, como nome completo e cidade.",
                     NivelCarta.PESADO, TipoCarta.CONTATO, arco3, // Supondo que arco3 é o arco atual
                     "https://i.imgur.com/4u4RDdg.png",
                     "Carta 1", // ID da carta
                     "Enviar as informações solicitadas.",
                     null, // Próxima carta (ID: #002)
                     2, // Próxima carta
                     "Perguntar ao streamer publicamente se o convite é real.",
                     null, // Próxima carta (ID: #004)
                     4, // Próxima carta
                     LadoCorreto.DIREITA), // Lado correto

               createCarta("Convite Perigoso",
                     "Depois de enviar suas informações, você recebe uma segunda mensagem pedindo mais dados, como endereço e telefone. O tom da mensagem fica mais insistente.",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco3,
                     "https://i.imgur.com/JYSdQQr.png",
                     "Carta 2", // ID da carta
                     "Parar de responder e contar para um adulto.",
                     null, // Final negativo
                     -1, // Próxima carta
                     "Continuar conversando e fornecer mais dados.",
                     null, // Próxima carta (ID: #006)
                     6, // Próxima carta
                     LadoCorreto.ESQUERDA), // Lado correto

               createCarta("Alerta Feito",
                     "Com a ajuda de um adulto, você reporta a conta falsa à plataforma e avisa seus amigos sobre o risco. A conta é removida, e você evita que outros caiam no golpe.",
                     NivelCarta.LEVE, TipoCarta.CONDUTA, arco3,
                     "https://i.imgur.com/6seVzjf.png",
                     "Carta 3", // ID da carta
                     "Continuar denunciando contas suspeitas.",
                     null, // Final positivo
                     -1, // Próxima carta
                     "Apenas cuidar da própria segurança.",
                     null, // Final negativo
                     -1, // Próxima carta
                     LadoCorreto.ESQUERDA), // Lado correto

               createCarta("Segurança Reforçada",
                     "Você descobre que o convite era uma tentativa de golpe e alerta seus amigos no grupo de WhatsApp. Todos ficam mais atentos ao receber mensagens de estranhos.",
                     NivelCarta.MEDIO, TipoCarta.CONTEUDO, arco3,
                     "https://i.imgur.com/JKba5XH.png",
                     "Carta 4", // ID da carta
                     "Criar dicas de segurança para seus amigos.",
                     null, // Final positivo
                     -1, // Próxima carta
                     "Apenas deixar o grupo alerta sem muitas explicações.",
                     null, // Final negativo
                     -1, // Próxima carta
                     LadoCorreto.ESQUERDA), // Lado correto

               createCarta("Impacto Positivo",
                     "Seu tutorial online viraliza! Muitas pessoas aprendem sobre golpes online e como se proteger. Você recebe mensagens de agradecimento de pessoas que você ajudou a evitar problemas.",
                     NivelCarta.MEDIO, TipoCarta.CONDUTA, arco3,
                     "https://i.imgur.com/GVBn1Tm.png",
                     "Carta 5", // ID da carta
                     "Continuar produzindo conteúdo sobre segurança online.",
                     null, // Final positivo
                     -1, // Próxima carta
                     "Voltar à sua rotina, satisfeito com o impacto que causou.",
                     null, // Final negativo
                     -1, // Próxima carta
                     LadoCorreto.ESQUERDA), // Lado correto

               createCarta("Falsa Segurança",
                     "Apesar do convite suspeito, você acaba clicando em um link suspeito enviado pelo golpista.",
                     NivelCarta.LEVE, TipoCarta.CONTATO, arco3,
                     "https://i.imgur.com/NbaQt6t.png",
                     "Carta 6", // ID da carta
                     "Perceber o erro imediatamente e fechar a página, além de reportar o link.",
                     null, // Final positivo
                     -1, // Próxima carta
                     "Continuar navegando na página e fornecer informações adicionais.",
                     null, // Final negativo
                     -1, // Próxima carta
                     LadoCorreto.ESQUERDA) // Lado correto
         );

         // Salvando cartas no arco
         arco3.setConjuntoCartas(cartasArco3);

         // Feedbacks para o Arco 3
         Feedback feedbackArco3PosFeedback = createFeedback(
               "Impressionante! Você foi esperto(a) e cuidadoso(a) ao lidar com mensagens de estranhos online. Você soube identificar o perigo, pedir ajuda e proteger a si mesmo(a) e aos seus amigos! Você é um(a) verdadeiro(a) herói(na) da internet!",
               FeedbackTipo.POSITIVO, arco3);

         Feedback feedbackArco3NegFeedback = createFeedback(
               "Algumas das suas escolhas online te colocaram em situações arriscadas. É importante lembrar que nem todos online são amigos, e nem todas as mensagens são verdadeiras. Converse com um adulto de confiança sobre o que aconteceu para aprender como se proteger melhor da próxima vez. Lembre-se: nunca compartilhar informações pessoais com pessoas que você não conhece online!",
               FeedbackTipo.NEGATIVO, arco3);

         // Salvando feedbacks no arco
         arco3.setConjuntoFeedback(List.of(feedbackArco3PosFeedback, feedbackArco3NegFeedback));

         // Salvando no repositório
         arcoRepository.saveAll(List.of(arco1, arco2, arco3));

         System.out.println("Arcos e cartas foram salvos no repositório com sucesso!");
      }
      carregarRelatoriosTurmaEAlunos();
   }

   private void carregarRelatoriosTurmaEAlunos() {
      if (relatorioTurmaRepository.count() == 0) {
         // Criando Relatório de Turma 1
         RelatorioTurma relatorioTurma1 = new RelatorioTurma();
         relatorioTurma1.setIdTurma((long) 101);
         relatorioTurma1.setIdProfessor(1L);
         relatorioTurmaRepository.save(relatorioTurma1);

         // Criando Relatório de Turma 2
         RelatorioTurma relatorioTurma2 = new RelatorioTurma();
         relatorioTurma2.setIdTurma((long) 102);
         relatorioTurma2.setIdProfessor(2L);
         relatorioTurmaRepository.save(relatorioTurma2);

         System.out.println("Relatórios de Turma foram salvos no repositório com sucesso!");

         // Criando Relatórios de Aluno (após salvar os relatórios de turma)
         if (relatorioAlunoRepository.count() == 0) {
            // Criando Relatório de Aluno 1
            RelatorioAluno aluno1 = new RelatorioAluno();
            aluno1.setRelatorioTurmaId(relatorioTurma1.getId()); // Usar o objeto RelatorioTurma
            aluno1.setIdAluno(1L);
            relatorioAlunoRepository.save(aluno1);

            // Criando Relatório de Aluno 2
            RelatorioAluno aluno2 = new RelatorioAluno();
            aluno2.setRelatorioTurmaId(relatorioTurma1.getId()); // Usar o objeto RelatorioTurma
            aluno2.setIdAluno(2L);
            relatorioAlunoRepository.save(aluno2);

            // Criando Relatório de Aluno 3
            RelatorioAluno aluno3 = new RelatorioAluno();
            aluno3.setRelatorioTurmaId(relatorioTurma2.getId()); // Usar o objeto RelatorioTurma
            aluno3.setIdAluno(3L);
            relatorioAlunoRepository.save(aluno3);

            System.out.println("Relatórios de Aluno foram salvos no repositório com sucesso!");
         }
      }
   }

   // Método auxiliar para criar cartas
   private Carta createCarta(String titulo, String descricao, NivelCarta nivel, TipoCarta tipo, Arco arco,
         String imagem, String identificador, String escolhaEsq, String resultadoEsq,
         int proxCartaEsq, String escolhaDir, String resultadoDir, int proxCartaDir,
         LadoCorreto ladoCorreto) {
      Carta carta = new Carta();
      carta.setNomeCarta(titulo);
      carta.setDescricao(descricao);
      carta.setNivelCarta(nivel);
      carta.setTipoCarta(tipo);
      carta.setUrlImagem(imagem);
      carta.setIdentificador(identificador);
      carta.setArco(arco);
      carta.setMensagemDireita(escolhaDir);
      carta.setMensagemEsquerda(escolhaEsq);
      carta.setIdCartaDire(proxCartaDir);
      carta.setIdCartaEsq(proxCartaEsq);
      carta.setLadoCorreto(ladoCorreto);

      return carta;
   }

   private Feedback createFeedback(String descricao, FeedbackTipo tipo, Arco arco) {
      Feedback feedback = new Feedback(descricao, tipo, arco);
      return feedback;
   }
}
