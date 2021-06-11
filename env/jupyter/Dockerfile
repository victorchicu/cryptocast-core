# Copyright (c) Jupyter Development Team.
# Distributed under the terms of the Modified BSD License.
FROM jupyter/tensorflow-notebook

LABEL maintainer="Jupyter Project <victor.chicu@hotmail.com>"

# Install Tensorflow
RUN mamba install --quiet --yes \
    'tensorflow=2.4.1' && \
    mamba clean --all -f -y && \
    fix-permissions "${CONDA_DIR}" && \
    fix-permissions "/home/${NB_USER}"
