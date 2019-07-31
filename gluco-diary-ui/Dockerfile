FROM node:12.7.0 as build

WORKDIR /app
COPY package.json /app/package.json
RUN npm install
RUN npm install -g @angular/cli@8.1.2
COPY . /app
RUN ng build --prod --output-path=dist

FROM nginx:1.16.0-alpine

COPY --from=build /app/dist /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80
