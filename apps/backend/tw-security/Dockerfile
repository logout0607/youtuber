FROM fsa.com:5443/tomcat

RUN rm -rf webapps/ROOT
ADD ./build/exploded webapps/ROOT

EXPOSE 8080
CMD ["catalina.sh", "run"]
