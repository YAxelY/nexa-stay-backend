up:
	sudo docker compose -f infra/docker-compose.yml up -d

down:
	sudo docker compose -f infra/docker-compose.yml down

logs:
	sudo docker compose -f infra/docker-compose.yml logs -f

ps:
	sudo docker compose -f infra/docker-compose.yml ps
