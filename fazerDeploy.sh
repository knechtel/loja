#!/bin/bash
clear

echo "Matando as aplicações startadas..."
killall java
echo "OK..."

echo "Iniciando o build das aplicações."
echo "Realizar o build do modulo acnf-core..."

cd core
mvn clean install -DskipTests
cd ..
echo "OK"

#### Loja
echo "Publicando a loja acnf..."
cd loja
mvn clean process-resources -DskipTests 
echo "Alterando o favicon.ico"
cp -f target/classes/static/favicon_acnf.ico target/classes/static/favicon.ico
mvn package -DskipTests
cp -f target/acnf-loja-0.0.1-SNAPSHOT.jar /var/www/acnf/
cp -f src/main/resources/application-prod.properties /var/www/acnf/
echo "loja.perfil=acnf" >> /var/www/acnf/application-prod.properties
echo "server.port=8080" >> /var/www/acnf/application-prod.properties
echo "OK"

#### Agro
echo "Publicando a loja agro..."
mvn clean process-resources -DskipTests 
echo "Alterando o favicon.ico"
cp -f target/classes/static/favicon_acnf_agro.ico target/classes/static/favicon.ico
mvn package -DskipTests
cp -f target/acnf-loja-0.0.1-SNAPSHOT.jar /var/www/acnfagro/
cp -f src/main/resources/application-prod.properties /var/www/acnfagro/
echo "loja.perfil=agro" >> /var/www/acnfagro/application-prod.properties
echo "server.port=8081" >> /var/www/acnfagro/application-prod.properties
cd ..
echo "OK"

#### Admin
echo "Publicando o Admin..."
cd admin
mvn clean install -DskipTests
cp -f target/acnf-loja-admin-0.0.1-SNAPSHOT.jar /var/www/admin/
cp -f src/main/resources/application-prod.properties /var/www/admin/
cd ..
echo "OK"
echo "Todos os arquivos publicados."

echo "Criando executáveis..."
echo "java -jar -Dspring.profiles.active=prod /var/www/admin/acnf-loja-admin-0.0.1-SNAPSHOT.jar" > /var/www/admin/start.sh
echo "java -jar -Dspring.profiles.active=prod /var/www/acnf/acnf-loja-0.0.1-SNAPSHOT.jar" > /var/www/acnf/start.sh
echo "java -jar -Dspring.profiles.active=prod /var/www/acnfagro/acnf-loja-0.0.1-SNAPSHOT.jar" > /var/www/acnfagro/start.sh
echo "Você deve inicializar o script start.sh em cada uma das pastas, da seguinte maneira:"
echo "sh /var/www/admin/start.sh"
echo "sh /var/www/acnf/start.sh"
echo "sh /var/www/acnfagro/start.sh"
echo "Ou caso você queira que o processo start em background utilize:"
echo "sh /var/www/admin/start.sh &"
echo "sh /var/www/acnf/start.sh &"
echo "sh /var/www/acnfagro/start.sh &"
echo "  "
echo "Deseja que o script tente executar os sistemas?"
echo "1-Sim"
echo "2-Não"
read opcao
if [ "$opcao" -eq 1 ]
then
    cd /var/www/admin/
    sh /var/www/admin/start.sh &
    sleep 15
    cd /var/www/acnf/
    sh /var/www/acnf/start.sh &
    sleep 15
    cd /var/www/acnfagro/
    sh /var/www/acnfagro/start.sh &
fi
cd ~/projetos/acnf-loja/projeto-loja
echo "Tchau..."
