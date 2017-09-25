import re

def process(s):
    s = s.strip()
    s = re.sub(r'&.+;', '', s)
    s = re.sub(r'\*+', '', s)
    s = re.sub(r'\s+', ' ', s)
    return s
