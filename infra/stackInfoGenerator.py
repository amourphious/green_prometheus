#!/usr/bin/python3

import argparse
import random
import string
import uuid
import json
STACK_TEMPLATE = '''{{
    "id": {id},
    "name": "arn:aws:cloudformation:{region}:254306680133:stack/{name}/{uuid}",
    "region": "{region}",
    "resources": [
        {resources}
    ]
}}'''

EC2_TEMPLATE = '''{{
    "id": {id},
    "name": "{name}",
    "resourceType": "AWS::EC2::Instance",
    "status": "CREATE_COMPLETE"
}}'''

ECS_TEMPLATE = '''{{
    "id": {id},
    "name": "{name}",
    "resourceType": "AWS::ECS::Cluster",
    "status": "CREATE_COMPLETE"
}}'''

EKS_TEMPLATE = '''{{
    "id": {id},
    "name": "{name}",
    "resourceType": "AWS::EKS::Cluster",
    "status": "CREATE_COMPLETE"
}}'''

ELASTICACHE_TEMPLATE = '''{{
    "id": {id},
    "name": "{name}",
    "resourceType": "AWS::ElastiCache::CacheCluster",
    "status": "CREATE_COMPLETE"
}}'''

AWS_SERVICES = ['EC2', 'ECS', 'EKS', 'ElastiCache']

regs = [
        "us-east-1",
        "us-east-2",
        "us-west-1",
        "us-west-2",
        "ca-central-1",
        "eu-north-1",
        "eu-west-1",
        "eu-west-2",
        "eu-west-3",
        "eu-central-1",
        "eu-south-1",
        "af-south-1",
        "ap-northeast-1",
        "ap-northeast-2",
        "ap-northeast-3",
        "ap-southeast-1",
        "ap-southeast-2",
        "ap-southeast-3",
        "ap-east-1",
        "ap-south-1",
        "sa-east-1",
        "me-south-1",
        "cn-north-1",
        "cn-northwest-1"
    ]

def random_region():
    return random.choice(regs)


def random_aws_service():
    return random.choice(AWS_SERVICES)


def random_id():
    return random.randint(1, 99999999)


def random_name():
    return ''.join(random.choices(string.ascii_letters, k=12))


def generate_ec2_resource():
    return EC2_TEMPLATE.format(id=random_id(), name=random_name())


def generate_ec2_resources(count):
    return [ generate_ec2_resource() for _ in range(count) ]


def generate_ecs_resource():
    return ECS_TEMPLATE.format(id=random_id(), name=random_name())


def generate_ecs_resources(count):
    return [ generate_ecs_resource() for _ in range(count) ]


def generate_eks_resource():
    return EKS_TEMPLATE.format(id=random_id(), name=random_name())


def generate_eks_resources(count):
    return [ generate_eks_resource() for _ in range(count) ]


def generate_elasticache_resource():
    return ELASTICACHE_TEMPLATE.format(id=random_id(), name=random_name())


def generate_elasticache_resources(count):
    return [ generate_elasticache_resource() for _ in range(count) ]


def generate_stack_resource(region, resources):
    return STACK_TEMPLATE.format(
        id=random_id(),
        region=region,
        name=random_name(),
        uuid=uuid.uuid4(),
        resources=','.join(resources))


def main():
    parser = argparse.ArgumentParser('AWS CloudFormation Stack info generator')
    parser.add_argument('-t', '--type', choices=AWS_SERVICES, required=False, default=random_aws_service())
    parser.add_argument('-c', '--count', required=False, type=int, default=random.randint(2, 5))
    parser.add_argument('-r', '--region', required=False, default=random_region())
    args = parser.parse_args()
    stacks = "["
    for r in regs:
        resources = []
        for ec2 in generate_ec2_resources(1):
            resources.append(ec2)
        for ecs in generate_ecs_resources(1):
            resources.append(ecs)
        for eks in generate_eks_resources(1):
            resources.append(eks)
        for ec in generate_elasticache_resources(1):
            resources.append(ec)
        stacks += generate_stack_resource(r, resources) + ",\n"
    with open('stacks.json', mode='w') as res:
        res.write(stacks)



if __name__ == '__main__':
    main()
