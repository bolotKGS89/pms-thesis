FROM node:19
WORKDIR .
COPY . .
# Install app dependencies
RUN npm install
# Expose the port on which app will run
EXPOSE 3000
# Define the command to run app
CMD ["node", "app.js"]