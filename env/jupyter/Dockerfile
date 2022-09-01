ARG BASE_CONTAINER=jupyter/scipy-notebook
FROM $BASE_CONTAINER

LABEL maintainer="Cryptocast <victor.chicu@hotmail.com>"

RUN mamba install --quiet --yes 'tensorflow=2.4.1' && mamba clean --all -f -y && fix-permissions "${CONDA_DIR}" && fix-permissions "/home/${NB_USER}"

RUN mamba install keras
RUN mamba install numpy==1.19.5
