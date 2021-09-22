# Kubernetes CI AWS ![GitHub Workflow Status](https://img.shields.io/github/workflow/status/effetivo/java-springboot-k8s/okteto)
Projeto Java/Springboot de exemplo que implementa Continuos Integration num Servidor na AWS utilizando Jenkins.

## Jenkins (Local via Docker)

### Plugins
```
Plugins default
Docker (Habilita o uso do docker)
Docker Pipeline (Habilita o uso do docker)
Pipeline Utility Steps (Habilita o comando readMavenPom())
```

### Iniciando o Jenkins

```
docker-compose up -d
```

### Password inicial

```
docker exec my-jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### Finalizando o Jenkins

```
docker-compose down
```

### Configurando o Dockerhub

No final do build o Jenkins irá criar uma imagem docker e subí-la para o Dockerhub.

Para isso é necessário configurar as credenciais do Dockerhub.

#### Criando um Access Token no Dockerhub

- Faça login em sua conta do Dockerhub [Dockerhub](https://hub.docker.com), clique em *Account Settings* / *Security*

- Crie um *Novo Access Token*

#### Adicionando as Credenciais no Jenkins

- Faça login no Jenkins
- Entre em *Manage Jenkins* / *Manage Credentials* / Store: Jenkins | Domains: Global / *Global credentials (unrestricted)*
- Clique em *Add Credentials*
```
Kind: Username with password
Scope: Global (Jenkins, nodes, items, all child items, etc)
Username: <USERNAME_DO_DOCKERHUB>
Password: <ACCESS_TOKEN_CRIADO_ETAPA_ANTERIOR>
ID: effetivo-dockerhub
```

## Kubernetes no Kind (local)

### Instalando o Kind

Seguir os passos para [Instalação do Kind](https://kind.sigs.k8s.io/docs/user/quick-start#installation).

```
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.1/kind-linux-amd64
chmod +x ./kind
mv ./kind /usr/local/bin/kink
```

### Comandos Básicos

* Crianda um Cluster
```
kind create cluster --name my-cluster
```

* Apagando um Cluster
```
kind delete cluster --name my-cluster
```

* Listando todos Clusters
```
kind get clusters
```

### Instalando o Kubectl

Seguir os passos para [Instalação do Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/).

### Comandos Básicos

* Selecionado o Cluster Kubectl
```
kubectl cluster-info --context kink-my-cluster
```

* Listando Nodes
```
kubectl get nodes
```

* Listando Replicaset
```
kubectl get replicaset
```

* Listando Pods
```
kubectl get pods
```

* Apagando um Pod
```
kubectl delete pod <NAME>
```

* Visualizando Logs de um Pod
```
kubectl logs <POD_NAME>
```

* Aplicando um Arquivo
```
kubectl apply -f <FILENAME>.yml
```

* Rollback
```
kubectl rollout undo deployment <NAME>
```

* Histórico de Versões
```
kubectl rollout history deployment <NAME>
```

* Lista Services
```
kubectl get svc
```

* Mapear uma porta
```
kubectl port-forward <NAME> 8888:80
kubectl port-forward svc/kubernetes-aws-service 8888:80
```

* Whats
watch 'kubectl get services'